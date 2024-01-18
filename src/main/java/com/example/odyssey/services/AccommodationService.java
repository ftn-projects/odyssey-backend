package com.example.odyssey.services;

import com.example.odyssey.dtos.accommodations.AccommodationDTO;
import com.example.odyssey.dtos.statistics.AccommodationTotalStatsDTO;
import com.example.odyssey.dtos.statistics.MonthlyStatsDTO;
import com.example.odyssey.dtos.statistics.TotalStatsDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.Amenity;
import com.example.odyssey.entity.accommodations.AvailabilitySlot;
import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.User;
import com.example.odyssey.exceptions.accommodations.AccommodationNotFoundException;
import com.example.odyssey.repositories.AccommodationRepository;
import com.example.odyssey.repositories.AmenityRepository;
import com.example.odyssey.repositories.ReservationRepository;
import com.example.odyssey.repositories.UserRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;

@Service
public class AccommodationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private AmenityRepository amenityRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public static final String imagesDirPath = "src/main/resources/images/accommodations/";

    public List<Accommodation> getAll(
            String location,
            Long dateStart,
            Long dateEnd,
            Integer guestNumber,
            List<Long> amenities,
            String type,
            Double priceStart,
            Double priceEnd
    ) {

        LocalDateTime startDate = (dateStart != null) ? new ReservationService().convertToDate(dateStart) : null;
        LocalDateTime endDate = (dateEnd != null) ? new ReservationService().convertToDate(dateEnd) : null;
        Accommodation.Type accommodationType = (type != null) ? Accommodation.Type.valueOf(type.toUpperCase()) : null;
        location = (location != null) ? location.toUpperCase() : null;

        return accommodationRepository.findAllWithFilter(
                guestNumber, accommodationType, amenities, startDate, endDate, priceStart, priceEnd, location
        );
    }

    public List<Accommodation> getAllMM() {
        return accommodationRepository.findAll();
    }

    public Accommodation getOne(Long id) {
        return accommodationRepository.findById(id).orElseThrow(() -> new AccommodationNotFoundException(id));
    }

    public List<Accommodation> findByHostId(Long hostId) {
        return accommodationRepository.findAllByHostId(hostId);
    }

    public Accommodation save(Accommodation accommodation) {
        return accommodationRepository.save(accommodation);
    }

    public boolean slotsOverlap(Set<AvailabilitySlot> slots) {
        for (AvailabilitySlot i : slots)
            for (AvailabilitySlot j : slots)
                if (i != j && i.getTimeSlot().overlaps(j.getTimeSlot()))
                    return true;
        return false;
    }

    public byte[] getImage(Long id, String imageName) throws IOException {
        getOne(id); // id validation

        String accommodationDirPath = imagesDirPath + id;

        Path imageFilePath = Paths.get(accommodationDirPath, imageName);

        if (!Files.exists(imageFilePath) || Files.isDirectory(imageFilePath)) {
            throw new IOException("Image not found: " + imageName);
        }

        return Files.readAllBytes(imageFilePath);
    }

    public List<String> getImageNames(Long id) throws IOException {
        getOne(id); // id validation

        String accommodationDirPath = imagesDirPath + id;

        Path accommodationDir = Paths.get(accommodationDirPath);

        if (!Files.exists(accommodationDir) || !Files.isDirectory(accommodationDir)) {
            throw new IOException("Accommodation directory not found for id: " + id);
        }

        List<String> imageNames = new ArrayList<>();

        try (var stream = Files.walk(accommodationDir)) {
            stream.filter(Files::isRegularFile)
                    .forEach(filePath -> imageNames.add(filePath.getFileName().toString()));
        }

        return imageNames.stream().sorted().toList();
    }

    public List<Amenity> getAmenities() {
        return amenityRepository.findAll();
    }

    public Double getPriceForDateRange(Long accommodationID, Long startDateLong, Long endDateLong) {
        if (accommodationID == null || startDateLong == null || endDateLong == null)
            return (double) -1;
        LocalDateTime startDate = new ReservationService().convertToDate(startDateLong);
        LocalDateTime endDate = new ReservationService().convertToDate(endDateLong);
        return accommodationRepository.findPriceForDateRange(accommodationID, startDate, endDate);
    }

    public Double calculateTotalPrice(Long accommodationID, Long startDateLong, Long endDateLong, Integer guestNumber) {
        if (accommodationID == null || startDateLong == null || endDateLong == null)
            return (double) -1;
        LocalDateTime startDate = new ReservationService().convertToDate(startDateLong);
        LocalDateTime endDate = new ReservationService().convertToDate(endDateLong);
        Accommodation accommodation = getOne(accommodationID);

        long days = endDate.toLocalDate().toEpochDay() - startDate.toLocalDate().toEpochDay() + 1;
        Double priceForRange = accommodationRepository.findPriceForDateRange(accommodationID, startDate, endDate);
        if (priceForRange == null) {
            return (double) -1;
        }
        if (accommodation.getPricing() == Accommodation.PricingType.PER_NIGHT) {
            return (days * accommodationRepository.findPriceForDateRange(accommodationID, startDate, endDate));
        } else if (accommodation.getPricing() == Accommodation.PricingType.PER_PERSON)
            if (guestNumber != null && guestNumber > 0) {
                return (days * priceForRange * guestNumber);
            } else
                return (double) -1;
        else
            return (double) -1;
    }


    public byte[] generatePeriodStatsPdf(Long hostId, Long startDate, Long endDate) throws IOException, DocumentException {
        List<Accommodation> accommodations = findByHostId(hostId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if(hostId == null) return byteArrayOutputStream.toByteArray();


        Instant startInstant = Instant.ofEpochMilli(startDate);
        LocalDateTime startLocalDateTime = LocalDateTime.ofInstant(startInstant, ZoneOffset.UTC);

        Instant endInstant = Instant.ofEpochMilli(endDate);
        LocalDateTime endLocalDateTime = LocalDateTime.ofInstant(endInstant, ZoneOffset.UTC);

        TotalStatsDTO totalStatsDTO = generatePeriodStats(hostId, startDate, endDate);

        generatePdfDocument(byteArrayOutputStream, totalStatsDTO);

        return byteArrayOutputStream.toByteArray();
    }

    public byte[] generatePeriodStatsPdfAccommodation(Long accommodationId, Long startDate, Long endDate) throws IOException, DocumentException {
        Accommodation accommodation = getOne(accommodationId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if(accommodationId == null) return byteArrayOutputStream.toByteArray();


        Instant startInstant = Instant.ofEpochMilli(startDate);
        LocalDateTime startLocalDateTime = LocalDateTime.ofInstant(startInstant, ZoneOffset.UTC);

        Instant endInstant = Instant.ofEpochMilli(endDate);
        LocalDateTime endLocalDateTime = LocalDateTime.ofInstant(endInstant, ZoneOffset.UTC);

        AccommodationTotalStatsDTO totalStatsDTO = generatePeriodStatsAccommodation(accommodationId, startDate, endDate);

        generatePdfDocument(byteArrayOutputStream, totalStatsDTO);

        return byteArrayOutputStream.toByteArray();
    }

    private void generatePdfDocument(ByteArrayOutputStream byteArrayOutputStream, TotalStatsDTO totalStatsDTO) throws DocumentException {
        Document document = new Document();
        try {
            LocalDateTime startDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(totalStatsDTO.getStart()), ZoneId.systemDefault());
            LocalDateTime endDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(totalStatsDTO.getEnd()), ZoneId.systemDefault());
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            document.add(new Paragraph("Host: " + totalStatsDTO.getHost().getName() + " " + totalStatsDTO.getHost().getSurname()));
            document.add(new Paragraph("Accommodation statistics for period: " +
                    startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " - " +
                    endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            document.add(new Paragraph("Number of accommodations: " + totalStatsDTO.getTotalAccommodations()));
            document.add(new Paragraph("Number of reservations: " + totalStatsDTO.getTotalReservations()));


            for (MonthlyStatsDTO monthlyStatsDTO : totalStatsDTO.getMonthlyStats()) {
                document.add(new Paragraph("\n"));
                LocalDateTime monthDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(monthlyStatsDTO.getMonth()), ZoneOffset.UTC);
                document.add(new Paragraph("Reservations for " + monthDate.format(DateTimeFormatter.ofPattern("MMMM yyyy")) +
                        ": " + monthlyStatsDTO.getReservationsCount()));
                document.add(new Paragraph("Total income for " + monthDate.format(DateTimeFormatter.ofPattern("MMMM yyyy")) +
                        ": " + monthlyStatsDTO.getTotalIncome()));
            }

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void generatePdfDocument(ByteArrayOutputStream byteArrayOutputStream, AccommodationTotalStatsDTO totalStatsDTO) throws DocumentException {
        Document document = new Document();
        try {
            LocalDateTime startDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(totalStatsDTO.getStart()), ZoneId.systemDefault());
            LocalDateTime endDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(totalStatsDTO.getEnd()), ZoneId.systemDefault());
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            document.add(new Paragraph("Accommodation: " + totalStatsDTO.getAccommodationDTO().getTitle()));
            document.add(new Paragraph("Accommodation statistics for period: " +
                    startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " - " +
                    endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            document.add(new Paragraph("Number of reservations: " + totalStatsDTO.getTotalReservations()));


            for (MonthlyStatsDTO monthlyStatsDTO : totalStatsDTO.getMonthlyStatsDTO()) {
                document.add(new Paragraph("\n"));
                LocalDateTime monthDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(monthlyStatsDTO.getMonth()), ZoneOffset.UTC);
                document.add(new Paragraph("Reservations for " + monthDate.format(DateTimeFormatter.ofPattern("MMMM yyyy")) +
                        ": " + monthlyStatsDTO.getReservationsCount()));
                document.add(new Paragraph("Total income for " + monthDate.format(DateTimeFormatter.ofPattern("MMMM yyyy")) +
                        ": " + monthlyStatsDTO.getTotalIncome()));
            }

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public AccommodationTotalStatsDTO generatePeriodStatsAccommodation(Long accommodationId, Long startDate, Long endDate) {
        Accommodation accommodation = getOne(accommodationId);
        User host = userRepository.findById(accommodation.getHost().getId()).orElse(null);
        List<Reservation.Status> statuses = new ArrayList<>();
        statuses.add(Reservation.Status.ACCEPTED);
        Instant startInstant = Instant.ofEpochMilli(startDate);
        LocalDateTime startLocalDateTime = LocalDateTime.ofInstant(startInstant, ZoneOffset.UTC);

        Instant endInstant = Instant.ofEpochMilli(endDate);
        LocalDateTime endLocalDateTime = LocalDateTime.ofInstant(endInstant, ZoneOffset.UTC);
        List<Reservation> reservations = reservationRepository.findAllWithFilterButWithId(null, statuses, accommodationId, startLocalDateTime, endLocalDateTime);


        List<MonthlyStatsDTO> monthlyStats = calculateMonthlyStats(reservations, startDate, endDate);

        int totalReservations = reservations.size();

        return new AccommodationTotalStatsDTO(startDate, endDate, totalReservations, new AccommodationDTO(accommodation), monthlyStats);
    }

    public TotalStatsDTO generatePeriodStats(Long hostId, Long startDate, Long endDate) {
        List<Accommodation> accommodations = findByHostId(hostId);
        User host = userRepository.findById(hostId).orElse(null);
        if(!(host instanceof Host)) return new TotalStatsDTO();
        List<Reservation.Status> statuses = new ArrayList<>();
        statuses.add(Reservation.Status.ACCEPTED);
        Instant startInstant = Instant.ofEpochMilli(startDate);
        LocalDateTime startLocalDateTime = LocalDateTime.ofInstant(startInstant, ZoneOffset.UTC);

        Instant endInstant = Instant.ofEpochMilli(endDate);
        LocalDateTime endLocalDateTime = LocalDateTime.ofInstant(endInstant, ZoneOffset.UTC);
        List<Reservation> reservations = reservationRepository.findAllWithFilter(hostId, statuses, null, startLocalDateTime, endLocalDateTime);


        List<MonthlyStatsDTO> monthlyStats = calculateMonthlyStats(reservations, startDate, endDate);

        int totalAccommodations = accommodations.size();
        int totalReservations = reservations.size();

        return new TotalStatsDTO(startDate, endDate, new UserDTO(host), totalAccommodations, totalReservations, monthlyStats);
    }
    private List<MonthlyStatsDTO> calculateMonthlyStats(List<Reservation> reservations, Long startDate, Long endDate) {
        List<MonthlyStatsDTO> monthlyStats = new ArrayList<>();

        LocalDateTime currentMonth = LocalDateTime.ofInstant(Instant.ofEpochMilli(startDate), ZoneId.systemDefault()).withDayOfMonth(1);
        LocalDateTime endLocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(endDate), ZoneId.systemDefault()).withDayOfMonth(1);

        while (!currentMonth.isAfter(endLocalDateTime)) {
            long startOfMonthMillis = currentMonth.toInstant(ZoneOffset.UTC).toEpochMilli();
            long endOfMonthMillis = currentMonth.plusMonths(1).minusDays(1).toInstant(ZoneOffset.UTC).toEpochMilli();

            int monthlyReservationsCount = 0;
            double totalIncome = 0;

            for (Reservation reservation : reservations) {
                LocalDateTime reservationStartDate = reservation.getTimeSlot().getStart();
                LocalDateTime reservationEndDate = reservation.getTimeSlot().getEnd();

                if (reservationEndDate.isAfter(LocalDateTime.ofInstant(Instant.ofEpochMilli(startOfMonthMillis), ZoneId.systemDefault())) &&
                        reservationEndDate.isBefore(LocalDateTime.ofInstant(Instant.ofEpochMilli(endOfMonthMillis), ZoneId.systemDefault()))) {
                    monthlyReservationsCount++;
                    totalIncome += reservation.getPrice();
                }
            }

            MonthlyStatsDTO monthlyStatsDTO = new MonthlyStatsDTO();
            monthlyStatsDTO.setMonth(startOfMonthMillis);
            monthlyStatsDTO.setReservationsCount(monthlyReservationsCount);
            monthlyStatsDTO.setTotalIncome(totalIncome);
            monthlyStats.add(monthlyStatsDTO);
            currentMonth = currentMonth.plusMonths(1);

        }

        return monthlyStats;
    }

    public List<AccommodationTotalStatsDTO> getAllAccommodationStats(Long hostId, Long startDate, Long endDate){
        List<Accommodation> accommodations = findByHostId(hostId);
        List<AccommodationTotalStatsDTO> accommodationTotalStatsDTOS = new ArrayList<>();
        for(Accommodation accommodation : accommodations){
            accommodationTotalStatsDTOS.add(generatePeriodStatsAccommodation(accommodation.getId(), startDate, endDate));
        }
        return accommodationTotalStatsDTOS;

    }
}
