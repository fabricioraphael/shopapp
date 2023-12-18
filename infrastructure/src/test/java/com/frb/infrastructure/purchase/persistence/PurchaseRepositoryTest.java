package com.frb.infrastructure.purchase.persistence;

import com.frb.domain.purchase.Purchase;
import com.frb.domain.purchase.PurchaseAmount;
import com.frb.infrastructure.MySQLGatewayTest;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.time.LocalDate;

@MySQLGatewayTest
public class PurchaseRepositoryTest {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Test
    public void givenAnInvalidNullName_whenCallsSave_shouldReturnError() {
        final var expectedPropertyName = "name";
        final var expectedMessage = "not-null property references a null or transient value : com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryJpaEntity.name";

        final var expectedDescription = "Purchase Description";
        final var purchaseDate = LocalDate.now();
        final var amount = PurchaseAmount.from(BigDecimal.valueOf(11.47));

        final var aPurchase = Purchase.newPurchase(expectedDescription, purchaseDate, amount);

        final var anEntity = PurchaseEntity.from(aPurchase);
        anEntity.setPurchaseDate(null);

        final var actualException =
                Assertions.assertThrows(DataIntegrityViolationException.class, () -> purchaseRepository.save(anEntity));

        final var actualCause =
                Assertions.assertInstanceOf(PropertyValueException.class, actualException.getCause());

        Assertions.assertEquals(expectedPropertyName, actualCause.getPropertyName());
        Assertions.assertEquals(expectedMessage, actualCause.getMessage());
    }
}
