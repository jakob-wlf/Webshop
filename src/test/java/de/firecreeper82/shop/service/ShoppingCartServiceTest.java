package de.firecreeper82.shop.service;

import de.firecreeper82.shop.exceptions.IdNotFoundException;
import de.firecreeper82.shop.model.*;
import de.firecreeper82.shop.repository.CustomerRepository;
import de.firecreeper82.shop.repository.OrderPositionRepository;
import de.firecreeper82.shop.repository.OrderRepository;
import de.firecreeper82.shop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ShoppingCartServiceTest {

    private ProductRepository productRepository;
    private ShoppingCartService service;

    @BeforeEach
    public void setupTests() {
        productRepository = mock(ProductRepository.class);
        service = new ShoppingCartService(
                mock(OrderRepository.class),
                mock(OrderPositionRepository.class),
                productRepository,
                mock(CustomerRepository.class)
        );
    }

    @Test
    public void testThatCalculateSumForEmptyCartReturnsDeliveryCost() {
        // when
        Long result = service.calculateSumForCart(
                new ArrayList<>(),
                500
        );

        // then
        assertThat(result).isEqualTo(500);
    }

    @Test
    public void testThatCalculateSumWithOneProductSumsPriceOfProduct() {
        // given
        ProductResponse savedProduct = new ProductResponse(UUID.randomUUID().toString(), "", "", 1000, new ArrayList<>());

        given(productRepository.findById(savedProduct.id())).willReturn(Optional.of(savedProduct));

        List<OrderPositionResponse> orderPositions = new ArrayList<>();
        addOrderPosition(orderPositions, savedProduct, 1);

        // when
        Long result = service.calculateSumForCart(orderPositions, 500);

        // then
        assertThat(result).isEqualTo(1500);
    }

    @Test
    public void testThatCalculateSumWithTwoProductSumsPricesOfProducts() {
        // given
        ProductResponse savedProduct1 = new ProductResponse(UUID.randomUUID().toString(), "", "", 1000, new ArrayList<>());
        ProductResponse savedProduct2 = new ProductResponse(UUID.randomUUID().toString(), "", "", 2000, new ArrayList<>());

        given(productRepository.findById(savedProduct1.id())).willReturn(Optional.of(savedProduct1));
        given(productRepository.findById(savedProduct2.id())).willReturn(Optional.of(savedProduct2));


        List<OrderPositionResponse> orderPositions = new ArrayList<>();
        addOrderPosition(orderPositions, savedProduct1, 1);
        addOrderPosition(orderPositions, savedProduct2, 4);

        // when
        Long result = service.calculateSumForCart(orderPositions, 500);

        // then
        assertThat(result).isEqualTo(9500);
    }

    @Test
    public void testThatCalculateSumWithNegativeQuantity() {
        // given
        ProductResponse savedProduct1 = new ProductResponse(UUID.randomUUID().toString(), "", "", 1000, new ArrayList<>());
        ProductResponse savedProduct2 = new ProductResponse(UUID.randomUUID().toString(), "", "", 2000, new ArrayList<>());

        given(productRepository.findById(savedProduct1.id())).willReturn(Optional.of(savedProduct1));
        given(productRepository.findById(savedProduct2.id())).willReturn(Optional.of(savedProduct2));

        List<OrderPositionResponse> orderPositions = new ArrayList<>();
        addOrderPosition(orderPositions, savedProduct1, 1);
        addOrderPosition(orderPositions, savedProduct2, -4);

        /// then
        assertThrows(IllegalArgumentException.class, () -> {
            //when
            service.calculateSumForCart(orderPositions, 500);
        });
    }

    @Test
    public void testThatCalculateSumWithNotExistingProductThrowsException() {
        // given
        ProductResponse notSavedProduct = new ProductResponse("", "", "", 1000, new ArrayList<>());

        List<OrderPositionResponse> orderPositions = new ArrayList<>();
        addOrderPosition(orderPositions, notSavedProduct, 1);


        // then
        assertThrows(IdNotFoundException.class, () -> {
            //when
            service.calculateSumForCart(orderPositions, 500);
        });
    }

    private static void addOrderPosition(List<OrderPositionResponse> orderPositions, ProductResponse savedProduct, int quantity) {
        orderPositions.add(new OrderPositionResponse("1", "orderId", savedProduct.id(), quantity));
    }

}
