package dev.speed.fastlib.tempdata.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public abstract class TempData {

    private final UUID user;

}
