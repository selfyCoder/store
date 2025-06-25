package kz.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.store.dto.response.ProductAndActionResponse;
import kz.store.service.ActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/actions")
@RequiredArgsConstructor
public class ActionController {
    private final ActionService actionService;

    @Operation(summary = """
             получение информации о товарах, участвующих в действующей на текущий момент акции
             (для поиска использовать период действия акции begin_date и end_date), метод должен работать в один поток
             """)
    @GetMapping("/active/products")
    public ResponseEntity<List<ProductAndActionResponse>> getProductsInActiveActions() {
        List<ProductAndActionResponse> result = actionService.getProductsInActiveActions();

        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(result);
    }
}
