package ru.practicum.ewm.service.filters;

import org.springframework.data.jpa.domain.Specification;
import ru.practicum.ewm.service.model.Event;
import ru.practicum.ewm.service.model.State;

import java.time.LocalDateTime;
import java.util.List;

public class EventSpecifications {

    public static Specification<Event> withUsers(List<Integer> userIds) {
        return (root, query, cb) -> userIds == null || userIds.isEmpty() ? null :
                root.get("initiator").get("id").in(userIds);
    }

    public static Specification<Event> withStates(List<String> states) {
        return (root, query, cb) -> states == null || states.isEmpty() ? null :
                root.get("state").in(states.stream().map(State::valueOf).toList());
    }

    public static Specification<Event> withCategories(List<Integer> categories) {
        return (root, query, cb) -> categories == null || categories.isEmpty() ? null :
                root.get("category").get("id").in(categories);
    }

    public static Specification<Event> withRangeStart(LocalDateTime rangeStart) {
        return (root, query, cb) -> rangeStart == null ? null :
                cb.greaterThanOrEqualTo(root.get("eventDate"), rangeStart);
    }

    public static Specification<Event> withRangeEnd(LocalDateTime rangeEnd) {
        return (root, query, cb) -> rangeEnd == null ? null :
                cb.lessThanOrEqualTo(root.get("eventDate"), rangeEnd);
    }

    public static Specification<Event> withText(String text) {
        return (root, query, cb) -> {
            if (text == null || text.isBlank()) return null;
            String likeText = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("annotation")), likeText),
                    cb.like(cb.lower(root.get("description")), likeText)
            );
        };
    }

    public static Specification<Event> withPaid(Boolean paid) {
        return (root, query, cb) -> paid == null ? null :
                cb.equal(root.get("paid"), paid);
    }


    public static Specification<Event> withOnlyAvailable(Boolean onlyAvailable) {
        return (root, query, cb) -> {
            if (onlyAvailable == null || !onlyAvailable) return null;
            return cb.or(
                    cb.lessThan(root.get("confirmedRequests"), root.get("participantLimit")),
                    cb.equal(root.get("participantLimit"), 0)
            );
        };
    }
}