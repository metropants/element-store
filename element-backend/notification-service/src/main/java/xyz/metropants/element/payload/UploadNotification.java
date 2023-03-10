package xyz.metropants.element.payload;

import java.io.Serializable;

public record UploadNotification(String name, byte[] data) implements Serializable {}
