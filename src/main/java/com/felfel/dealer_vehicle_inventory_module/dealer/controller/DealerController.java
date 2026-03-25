package com.felfel.dealer_vehicle_inventory_module.dealer.controller;

import com.felfel.dealer_vehicle_inventory_module.dealer.Data.Dealer;
import com.felfel.dealer_vehicle_inventory_module.dealer.converter.DealerToDealerResponseDtoConverter;
import com.felfel.dealer_vehicle_inventory_module.dealer.dto.DealerPatchRequestDto;
import com.felfel.dealer_vehicle_inventory_module.dealer.dto.DealerPostRequestDto;
import com.felfel.dealer_vehicle_inventory_module.dealer.dto.DealerResponseDto;
import com.felfel.dealer_vehicle_inventory_module.dealer.service.DealerService;
import com.felfel.dealer_vehicle_inventory_module.system.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("dealers")
public class DealerController {
    private final DealerService dealerService;
    private final DealerToDealerResponseDtoConverter dealerToDealerResponseDtoConverter;

    public DealerController(DealerService dealerService, DealerToDealerResponseDtoConverter dealerToDealerResponseDtoConverter) {
        this.dealerService = dealerService;
        this.dealerToDealerResponseDtoConverter = dealerToDealerResponseDtoConverter;
    }
    @GetMapping()
    public Result findAllDealers() {
        List<Dealer> dealers = dealerService.findAll();
        List<DealerResponseDto> foundDealerDtos = dealers
                .stream()
                .map(dealerToDealerResponseDtoConverter::convert)
                .toList();
        return new Result(true, HttpStatus.OK.value(), "find all success", foundDealerDtos);
    }

    @GetMapping("/{id}")
    public Result findDealerById(@PathVariable UUID id)
    {
        Dealer foundDealer= dealerService.findById(id);
        DealerResponseDto foundDealerDto = dealerToDealerResponseDtoConverter.convert(foundDealer);
        return new Result(true,HttpStatus.OK.value(), "find one success",foundDealerDto);
    }

    @PostMapping("")
    public Result addDealer(@RequestBody DealerPostRequestDto newDealer)
    {
        Dealer addedDealer = dealerService.add(newDealer);
        DealerResponseDto addedDealerDto = dealerToDealerResponseDtoConverter.convert(addedDealer);
        return new Result(true,HttpStatus.CREATED.value(), "add success",addedDealerDto);

    }
    @PatchMapping("/{id}")
    public Result updateDealer(@PathVariable UUID id, @RequestBody DealerPatchRequestDto updatedDealer)
    {
        Dealer updatedDealerData = dealerService.update(id, updatedDealer);
        DealerResponseDto updatedDealerDto = dealerToDealerResponseDtoConverter.convert(updatedDealerData);
        return new Result(true,HttpStatus.OK.value(), "update success",updatedDealerDto);
    }
    @DeleteMapping("/{id}")
    public Result deleteDealer(@PathVariable UUID id)
    {
        dealerService.delete(id);
        return new Result(true,HttpStatus.NO_CONTENT.value(), "delete success",null);

    }
}
