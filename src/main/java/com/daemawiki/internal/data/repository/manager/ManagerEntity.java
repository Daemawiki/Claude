package com.daemawiki.internal.data.repository.manager;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import static lombok.AccessLevel.PROTECTED;

@Setter
@Getter
@NoArgsConstructor(access = PROTECTED)
@Document(collection = "daemawiki-manager")
class ManagerEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true)
    private String userId;

    public void addUserId(String userId) { // TODO: 2/4/25  
        this.userId = userId;
    }

}
