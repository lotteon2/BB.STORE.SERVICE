package kr.bb.store.domain.cargo.controller;

import kr.bb.store.domain.cargo.controller.request.StockFeignRequest;
import kr.bb.store.domain.cargo.service.CargoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/stores/flowers/stocks")
public class CargoFeignController {
    private final CargoService cargoService;
    @PutMapping("/add")
    public ResponseEntity addStock(@RequestBody StockFeignRequest stockFeignRequest) {
        cargoService.PlusStockCount(stockFeignRequest.getStoreId(), stockFeignRequest.getFlowerId(), stockFeignRequest.getStock());
        return ResponseEntity.ok().build();
    }
}
