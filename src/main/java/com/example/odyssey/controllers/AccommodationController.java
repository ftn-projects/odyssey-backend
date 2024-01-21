package com.example.odyssey.controllers;

import com.example.odyssey.dtos.accommodations.AccommodationDTO;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.services.AccommodationService;
import com.example.odyssey.services.ReviewService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/accommodations")
public class AccommodationController {
    @Autowired
    private AccommodationService service;

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Long dateStart,
            @RequestParam(required = false) Long dateEnd,
            @RequestParam(required = false) Integer guestNumber,
            @RequestParam(required = false) List<Long> amenities,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Double priceStart,
            @RequestParam(required = false) Double priceEnd
    ) {
        List<Accommodation> accommodations;
        accommodations = service.getAll(location, dateStart, dateEnd, guestNumber, amenities, type, priceStart, priceEnd);
        List<AccommodationDTO> AccommodationDTOs = mapToDTO(accommodations);
        for (AccommodationDTO accommodationDTO : AccommodationDTOs) {
            accommodationDTO.setTotalPrice(service.calculateTotalPrice(accommodationDTO.getId(), dateStart, dateEnd, guestNumber));
            accommodationDTO.setDefaultPrice(service.getPriceForDateRange(accommodationDTO.getId(), dateStart, dateEnd));
            accommodationDTO.setAverageRating(reviewService.getTotalRatingByAccommodation(accommodationDTO.getId()));
        }
        return new ResponseEntity<>(AccommodationDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccommodationDTO> findById(@PathVariable Long id) {
        AccommodationDTO accommodationDTO = new AccommodationDTO(service.findById(id));
        accommodationDTO.setAverageRating(reviewService.getTotalRatingByAccommodation(accommodationDTO.getId()));
        return new ResponseEntity<>(accommodationDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}/totalPrice")
    public ResponseEntity<AccommodationDTO> findByDateAndGuests(
            @PathVariable Long id,
            @RequestParam(required = false) Long dateStart,
            @RequestParam(required = false) Long dateEnd,
            @RequestParam(required = false) Integer guestNumber
    ) {
        Accommodation accommodation = service.findById(id);
        AccommodationDTO accommodationDTO = new AccommodationDTO(service.findById(id));

        accommodationDTO.setTotalPrice(service.calculateTotalPrice(accommodation.getId(), dateStart, dateEnd, guestNumber));
        accommodationDTO.setDefaultPrice(service.getPriceForDateRange(accommodation.getId(), dateStart, dateEnd));
        accommodationDTO.setAverageRating(reviewService.getTotalRatingByAccommodation(accommodationDTO.getId()));

        return new ResponseEntity<>(accommodationDTO, HttpStatus.OK);
    }

    @GetMapping("/host/{id}")
    public ResponseEntity<?> findByHostId(@PathVariable Long id) {
        List<Accommodation> accommodations = new ArrayList<>();
        accommodations = service.findByHostId(id);
        List<AccommodationDTO> dtos = mapToDTO(accommodations);
        for (AccommodationDTO dto : dtos) {
            dto.setAverageRating(reviewService.getTotalRatingByAccommodation(dto.getId()));
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

//    @PreAuthorize("hasAuthority('GUEST')")
    @GetMapping("/favorites/{id}")
    public ResponseEntity<?> findByGuestFavorites(@PathVariable Long id) {
        List<Accommodation> accommodations = new ArrayList<>();
        accommodations = service.findByGuestFavorites(id);
        List<AccommodationDTO> dtos = mapToDTO(accommodations);
        for (AccommodationDTO dto : dtos) {
            dto.setAverageRating(reviewService.getTotalRatingByAccommodation(dto.getId()));
        }
        return new ResponseEntity<>(mapToDTO(accommodations), HttpStatus.OK);
    }

    @PutMapping(value="favorites/{guestId}/{accommodationId}")
    public ResponseEntity<?> addGuestFavorites(
            @PathVariable Long guestId,
            @PathVariable Long accommodationId
            ){
        service.addGuestFavorite(guestId, accommodationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value="favorites/{guestId}/{accommodationId}")
    public ResponseEntity<?> removeGuestFavorites(
            @PathVariable Long guestId,
            @PathVariable Long accommodationId
    ){
        service.removeGuestFavorite(guestId, accommodationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/images/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getImage(@PathVariable Long id, @PathVariable String imageName) throws IOException {
        return new ResponseEntity<>(service.getImage(id, imageName), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/images")
    public ResponseEntity<?> getImages(@PathVariable Long id) throws IOException {
        return new ResponseEntity<>(service.getImageNames(id), HttpStatus.OK);
    }

    @GetMapping(value = "/amenities")
    public ResponseEntity<?> getAmenities() {
        return new ResponseEntity<>(service.getAmenities(), HttpStatus.OK);
    }


    @GetMapping(value = "/stats/host/{id}/file", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getPeriodStatsAsPdf(
            @PathVariable Long id,
            @RequestParam(required = false) Long startDate,
            @RequestParam(required = false) Long endDate
    ) {
        try {
            byte[] pdfBytes = service.generatePeriodStatsPdf(id,startDate,endDate);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "statistics.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/stats/accommodation/{id}/file", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getPeriodStatsForAccommodationAsPdf(
            @PathVariable Long id,
            @RequestParam(required = false) Long startDate,
            @RequestParam(required = false) Long endDate
    ) {
        try {
            byte[] pdfBytes = service.generatePeriodStatsPdfAccommodation(id,startDate,endDate);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "statistics.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/stats/accommodation/{id}")
    public ResponseEntity<?> getPeriodStatsForAccommodation(
            @PathVariable Long id,
            @RequestParam(required = false) Long startDate,
            @RequestParam(required = false) Long endDate
    ) {
        return new ResponseEntity<>(service.generatePeriodStatsAccommodation(id, startDate, endDate), HttpStatus.OK);
    }

    @GetMapping(value = "/stats/host/{id}")
    public ResponseEntity<?> getPeriodStats(
            @PathVariable Long id,
            @RequestParam(required = false) Long startDate,
            @RequestParam(required = false) Long endDate
    ) {
        return new ResponseEntity<>(service.generatePeriodStats(id, startDate, endDate), HttpStatus.OK);
    }

    @GetMapping(value = "/stats/host/{id}/all")
    public ResponseEntity<?> getPeriodStatsAllAccommodation(
            @PathVariable Long id,
            @RequestParam(required = false) Long startDate,
            @RequestParam(required = false) Long endDate
    ) {
        return new ResponseEntity<>(service.getAllAccommodationStats(id, startDate, endDate), HttpStatus.OK);
    }
    private static List<AccommodationDTO> mapToDTO(List<Accommodation> accommodations) {
        return accommodations.stream().map(AccommodationDTO::new).toList();
    }
}
