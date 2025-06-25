package kz.store.dto.response;


public record ClientResponse(
        Long id,
        String firstName,
        String lastName,
        String middleName,
        String iin
) {
}
