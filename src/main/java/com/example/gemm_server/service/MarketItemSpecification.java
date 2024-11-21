package com.example.gemm_server.service;

import com.example.gemm_server.common.enums.Category;
import com.example.gemm_server.common.enums.MaterialType;
import com.example.gemm_server.common.util.MaterialUtil;
import com.example.gemm_server.domain.entity.MarketItem;
import jakarta.persistence.criteria.CriteriaBuilder;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class MarketItemSpecification {

  public static Specification<MarketItem> isFree(Boolean isFree) {
    return (root, query, criteriaBuilder) -> {
      if (isFree == null) {
        return criteriaBuilder.conjunction();
      }
      if (isFree) {
        return criteriaBuilder.equal(root.get("price"), 0);
      }
      return criteriaBuilder.notEqual(root.get("price"), 0);
    };
  }

  public static Specification<MarketItem> hasActivityAge(List<Short> ages) {
    return (root, query, criteriaBuilder) -> {
      if (ages == null || ages.isEmpty()) {
        return criteriaBuilder.conjunction();
      }

      CriteriaBuilder.In<Short> inClause = criteriaBuilder.in(root.get("activity").get("age"));
      for (Short age : ages) {
        inClause.value(age);
      }
      return inClause;
    };
  }

  public static Specification<MarketItem> hasActivityCategory(List<Category> categories) {

    return (root, query, criteriaBuilder) -> {
      if (categories == null || categories.isEmpty()) {
        return criteriaBuilder.conjunction();
      }
      CriteriaBuilder.In<Category> inClause = criteriaBuilder.in(
          root.get("activity").get("category"));
      for (Category category : categories) {
        inClause.value(category);
      }
      return inClause;
    };
  }

  public static Specification<MarketItem> hasActivityMaterialType(
      List<MaterialType> materialTypes) {

    return (root, query, criteriaBuilder) -> {
      if (materialTypes == null || materialTypes.isEmpty()) {
        return criteriaBuilder.conjunction();
      }
      short materialTypeBitMask = MaterialUtil.getMaterialBitMask(materialTypes);
      // SQL: (materialType & materialTypeBitMask) = materialTypeBitMask
      return criteriaBuilder.equal(
          criteriaBuilder.function(
              "bitand", Short.class,
              root.get("activity").get("materialType"),
              criteriaBuilder.literal(materialTypeBitMask)
          ),
          materialTypeBitMask
      );
    };
  }

  public static Specification<MarketItem> hasYear(Integer year) {
    return (root, query, criteriaBuilder) -> {
      if (year == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(root.get("year"), year);
    };
  }

  public static Specification<MarketItem> hasMonth(Short month) {
    return (root, query, criteriaBuilder) -> {
      if (month == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(root.get("month"), month);
    };
  }

  public static Specification<MarketItem> hasTitle(String title) {
    if (title == null || title.trim().isEmpty()) {
      return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction(); // No filter if title is empty
    }
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.like(criteriaBuilder.lower(root.get("activity").get("title")),
            "%" + title.toLowerCase() + "%");
  }

  public static Specification<MarketItem> hasOwner(String nickname) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.like(root.get("owner").get("nickname"), "%" + nickname + "%");
  }

  public static Specification<MarketItem> alwaysTrue() {
    return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
  }
}