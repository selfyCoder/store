package kz.store.service;

import kz.store.Repository.ActionRepository;
import kz.store.dto.response.ProductAndActionResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ActionService {
    private final ActionRepository actionRepository;

    public List<ProductAndActionResponse> getProductsInActiveActions() {
        return actionRepository.findProductsWithActiveActions();
    }
}
