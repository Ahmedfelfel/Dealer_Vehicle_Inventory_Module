package com.felfel.dealer_vehicle_inventory_module.dealer.converter;

import com.felfel.dealer_vehicle_inventory_module.dealer.Data.Dealer;
import com.felfel.dealer_vehicle_inventory_module.dealer.dto.DealerResponseDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converter that maps a {@link Dealer} entity to a {@link DealerResponseDto}.
 */
@Component
public class DealerToDealerResponseDtoConverter implements Converter<Dealer, DealerResponseDto> {
    /**
     * Converts a Dealer entity to its response DTO representation.
     *
     * @param source The Dealer entity.
     * @return The converted DealerResponseDto.
     */
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
