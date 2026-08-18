package com.sankalpapp.dto.mapper;

import com.sankalpapp.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class StudentSpecification {

    private StudentSpecification() {
    }

    public static Specification<Student> filter(
            EntityManager entityManager,
            Long districtId,
            Long talukaId,
            Long centerId,
            String school,
            String studentClass,
            String medium,
            String gender,
            Boolean active,
            String search
    ) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            /*
             * District
             */
            if (districtId != null) {
                predicates.add(
                        cb.equal(
                                root.get("district").get("id"),
                                districtId
                        )
                );
            }

            /*
             * Taluka
             */
            if (talukaId != null) {
                predicates.add(
                        cb.equal(
                                root.get("taluka").get("id"),
                                talukaId
                        )
                );
            }

            /*
             * Center
             */
            if (centerId != null) {
                predicates.add(
                        cb.equal(
                                root.get("center").get("id"),
                                centerId
                        )
                );
            }

            /*
             * School
             */
            if (school != null && !school.trim().isEmpty()) {

                predicates.add(
                        cb.like(
                                cb.lower(root.get("school")),
                                "%" + school.trim().toLowerCase() + "%"
                        )
                );
            }

            /*
             * Student Class
             */
            if (studentClass != null &&
                    !studentClass.trim().isEmpty()) {

                predicates.add(
                        cb.equal(
                                root.get("studentClass"),
                                studentClass.trim()
                        )
                );
            }

            /*
             * Medium
             */
            if (medium != null && !medium.trim().isEmpty()) {

                predicates.add(
                        cb.equal(
                                cb.lower(root.get("medium")),
                                medium.trim().toLowerCase()
                        )
                );
            }

            /*
             * Gender
             */
            if (gender != null && !gender.trim().isEmpty()) {

                predicates.add(
                        cb.equal(
                                cb.lower(root.get("gender")),
                                gender.trim().toLowerCase()
                        )
                );
            }

            /*
             * Active
             */
            if (active != null) {

                predicates.add(
                        cb.equal(
                                root.get("active"),
                                active
                        )
                );
            }

            /*
             * Global Search
             */
            if (search != null && !search.trim().isEmpty()) {

                String searchValue =
                        "%" + search.trim().toLowerCase() + "%";

                EntityType<Student> entityType =
                        entityManager
                                .getMetamodel()
                                .entity(Student.class);

                List<Predicate> searchPredicates =
                        entityType
                                .getAttributes()
                                .stream()
                                .filter(attribute ->
                                        attribute.getJavaType()
                                                .equals(String.class)
                                )
                                .map(attribute ->
                                        cb.like(
                                                cb.lower(
                                                        root.get(attribute.getName())
                                                ),
                                                searchValue
                                        )
                                )
                                .toList();

                if (!searchPredicates.isEmpty()) {

                    predicates.add(
                            cb.or(
                                    searchPredicates.toArray(
                                            new Predicate[0]
                                    )
                            )
                    );
                }
            }

            /*
             * Combine all filters using AND
             */
            return cb.and(
                    predicates.toArray(
                            new Predicate[0]
                    )
            );
        };
    }
}