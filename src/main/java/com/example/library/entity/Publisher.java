package com.example.library.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    String address;
}
