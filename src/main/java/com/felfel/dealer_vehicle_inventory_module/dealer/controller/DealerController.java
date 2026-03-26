package com.felfel.dealer_vehicle_inventory_module.dealer.controller;

import com.felfel.dealer_vehicle_inventory_module.dealer.Data.Dealer;
import com.felfel.dealer_vehicle_inventory_module.dealer.Data.SubscriptionType;
import com.felfel.dealer_vehicle_inventory_module.dealer.converter.DealerToDealerResponseDtoConverter;
import com.felfel.dealer_vehicle_inventory_module.dealer.dto.DealerPatchRequestDto;
import com.felfel.dealer_vehicle_inventory_module.dealer.dto.DealerPostRequestDto;
import com.felfel.dealer_vehicle_inventory_module.dealer.dto.DealerResponseDto;
import com.felfel.dealer_vehicle_inventory_module.dealer.service.DealerService;
import com.felfel.dealer_vehicle_inventory_module.system.Result;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * The type Dealer controller.
 */
@RestController
@RequestMapping("dealers")
public class DealerController {
    private final DealerService dealerService;
    private final DealerToDealerResponseDtoConverter dealerToDealerResponseDtoConverter;

    /**
     * Instantiates a new Dealer controller.
     *
     * @param dealerService                      the dealer service
     * @param dealerToDealerResponseDtoConverter the dealer to dealer response dto converter
     */
    public DealerController(DealerService dealerService, DealerToDealerResponseDtoConverter dealerToDealerResponseDtoConverter) {
        this.dealerService = dealerService;
        this.dealerToDealerResponseDtoConverter = dealerToDealerResponseDtoConverter;
    }

    /**
     * Find all dealers result.
     *
     * @param pageable     the pageable
     * @param subscription the subscription
     * @return the result
     */
    @GetMapping()
    public Result findAllDealers(Pageable pageable, @RequestParam(required = false) SubscriptionType subscription) {
        Page<Dealer> dealers = dealerService.findAll(pageable, subscription);
        Page<DealerResponseDto> foundDealerDtos = dealers
                .map(dealerToDealerResponseDtoConverter::convert);
        return new Result(true, HttpStatus.OK.value(), "find all success", foundDealerDtos);
    }

    /**
     * Find dealer by id result.
     *
     * @param id the id
     * @return the result
     */
    @GetMapping("/{id}")
    public Result findDealerById(@PathVariable UUID id)
    {
        Dealer foundDealer= dealerService.findById(id);
        DealerResponseDto foundDealerDto = dealerToDealerResponseDtoConverter.convert(foundDealer);
        return new Result(true,HttpStatus.OK.value(), "find one success",foundDealerDto);
    }

    /**
     * Add dealer result.
     *
     * @param newDealer the new dealer
     * @return the result
     */
    @PostMapping("")
    public Result addDealer(@Valid @RequestBody DealerPostRequestDto newDealer)
    {
        Dealer addedDealer = dealerService.add(newDealer);
        DealerResponseDto addedDealerDto = dealerToDealerResponseDtoConverter.convert(addedDealer);
        return new Result(true,HttpStatus.CREATED.value(), "add success",addedDealerDto);

    }

    /**
     * Update dealer result.
     *
     * @param id            the id
     * @param updatedDealer the updated dealer
     * @return the result
     */
    @PatchMapping("/{id}")
    public Result updateDealer(@PathVariable UUID id, @RequestBody DealerPatchRequestDto updatedDealer)
    {
        Dealer updatedDealerData = dealerService.update(id, updatedDealer);
        DealerResponseDto updatedDealerDto = dealerToDealerResponseDtoConverter.convert(updatedDealerData);
        return new Result(true,HttpStatus.OK.value(), "update success",updatedDealerDto);
    }

    /**
     * Delete dealer result.
     *
     * @param id the id
     * @return the result
     */
    @DeleteMapping("/{id}")
    public Result deleteDealer(@PathVariable UUID id)
    {
        dealerService.delete(id);
        return new Result(true,HttpStatus.NO_CONTENT.value(), "delete success",null);

    }
}
