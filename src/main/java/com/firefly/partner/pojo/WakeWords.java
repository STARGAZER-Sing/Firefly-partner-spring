package com.firefly.partner.pojo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class WakeWords {
    private int WID;
    private String Word;
    private String ResponsePath;
    private String Response;
    private int Operation;
    private int Active;
    private int Cooldown;
    private String CreatePath;
}
