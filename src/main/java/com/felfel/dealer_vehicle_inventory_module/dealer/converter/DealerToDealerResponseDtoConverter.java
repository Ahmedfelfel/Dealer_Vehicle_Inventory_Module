package com.felfel.dealer_vehicle_inventory_module.dealer.converter;

import com.felfel.dealer_vehicle_inventory_module.dealer.Data.Dealer;
import com.felfel.dealer_vehicle_inventory_module.dealer.dto.DealerResponseDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DealerToDealerResponseDtoConverter implements Converter<Dealer, DealerResponseDto> {
    @Override
    public DealerResponseDto convert(Dealer source) {
        return new DealerResponseDto(
                source.getId(),
                source.getName(),
                source.getEmail(),
                source.getSubscriptionType()
        );
    }
}
