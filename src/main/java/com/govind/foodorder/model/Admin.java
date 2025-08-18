package com.govind.foodorder.model;

import com.govind.foodorder.enums.AdminLevel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Admin extends User {

    @Enumerated(EnumType.STRING)
    private AdminLevel adminLevel;

}
