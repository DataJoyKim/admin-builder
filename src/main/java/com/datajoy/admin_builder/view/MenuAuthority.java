package com.datajoy.admin_builder.view;

import com.datajoy.admin_builder.view.domain.Menu;
import com.datajoy.admin_builder.view.domain.ViewObject;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table
@Entity
public class MenuAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String authorityCode;

    @ManyToOne
    @JoinColumn(name = "MENU_ID")
    private Menu menu;
}
