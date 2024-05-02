package com.example.idutil.service;

import com.example.idutil.common.enums.IdType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * uuid
 *
 */
@Slf4j
@Component
public class UUIDHandler extends BaseIdHandler {

    public UUIDHandler() { idCode = IdType.UUID.getType(); }

    @Override
    public String doIdAction(String businessCode) {
        return UUID.randomUUID().toString();
    }
}
