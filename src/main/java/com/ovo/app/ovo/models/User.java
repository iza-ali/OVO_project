//package com.ovo.app.ovo.models;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;
//
//@Entity(name="User")
//public class User {
//    @Id
//    @GeneratedValue()
//    @Column(unique=true)
//    private Long id;
//
//    @Column(
//            nullable = false,
//            columnDefinition = "TEXT"
//    )
//    private String Name;
//
//    @Column(
//            nullable = false,
//            columnDefinition = "TEXT",
//            unique = true
//    )
//    private String email;
//
//    public Long getId() {
//        return id;
//    }
//    public void setId(Long id) {
//        this.id = id;
//    }
//    public String getName() {
//        return Name;
//    }
//    public void setName(String name) {
//        Name = name;
//    }
//    public String getEmail() {
//        return this.email;
//    }
//    public void setEmail(String email) {
//        this.email = email;
//    }
//}
//
