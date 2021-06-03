package com.thedariusz.warnme.twitter;

public class AuthorDto {
    private final String authorId;
    private final String name;
    private final String username;

    public static AuthorDto fake(String twitterUserId) {
        return new AuthorDto(twitterUserId, "imgw", "imgw ipb");
    }

    private AuthorDto(String authorId, String name, String username) {
        this.authorId = authorId;
        this.name = name;
        this.username = username;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

}
