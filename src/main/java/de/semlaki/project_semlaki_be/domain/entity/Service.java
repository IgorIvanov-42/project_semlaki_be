package de.semlaki.project_semlaki_be.domain.entity;

import java.util.Objects;

public class Service {

    private Long id;
    private String title;
    private Long user_id;
    private String photo;
    private String description;
    private Long category_id;

    public Service() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return Objects.equals(id, service.id) && Objects.equals(title, service.title) && Objects.equals(user_id, service.user_id) && Objects.equals(photo, service.photo) && Objects.equals(description, service.description) && Objects.equals(category_id, service.category_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, user_id, photo, description, category_id);
    }

    @Override
    public String toString() {
        return String.format("Service: id - %d, title - %s, user_id - %d, photo - %s, description - %s, category_id - %d", id, title, user_id, photo, description, category_id);
    }
}
