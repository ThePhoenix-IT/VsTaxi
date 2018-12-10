package com.hamzajg.vstaxi.core.domain.models;

import java.util.UUID;

public class Client {

    private enum ClientState {ACTIVE, DEACTIVATED}

    private UUID uuid;
    private String firstName;
    private String LastName;
    private String email;
    private String login;
    private ClientState clientState;

}
