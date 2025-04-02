package de.semlaki.project_semlaki_be.domain.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "services")
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 👈 Здесь должен быть объект User

    @Column(name = "photo")
    private String photo;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Categories category;

    public Services() {
    }

    public Services(String title, User user, String description, String photo, Categories category) {
        this.title = title;
        this.user = user;
        this.photo = photo;
        this.description = description;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Services services = (Services) o;
        return Objects.equals(id, services.id) &&
                Objects.equals(title, services.title) &&
                Objects.equals(user, services.user) &&
                Objects.equals(category, services.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, user, category);
    }

    @Override
    public String toString() {
        return String.format("Services: id - %d, title - %s, user - %s, photo - %s, description - %s, category - %s",
                id, title, user.getEmail(), photo, description, category.getTitle());
    }
}

