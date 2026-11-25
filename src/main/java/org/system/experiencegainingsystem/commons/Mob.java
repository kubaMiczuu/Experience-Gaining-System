package org.system.experiencegainingsystem.commons;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class Mob {
    private final Id mobId;
    private final Name name;
    private final Experience minExp;
    private final Experience maxExp;
}
