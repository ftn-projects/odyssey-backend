package com.example.odyssey.dtos.users;

import com.example.odyssey.entity.users.Host;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HostDTO extends UserDTO{
    String bio;
    public HostDTO(Host host){
        super(host);
        bio = host.getBio();
    }
}
