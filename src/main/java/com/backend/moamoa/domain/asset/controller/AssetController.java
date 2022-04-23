package com.backend.moamoa.domain.asset.controller;

import com.backend.moamoa.domain.asset.dto.request.*;
import com.backend.moamoa.domain.asset.dto.response.*;
import com.backend.moamoa.domain.asset.service.AssetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "가계부 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/assets")
public class AssetController {

    private final AssetService assetService;

    @ApiOperation(value = "가계부 설정 카테고리 조회", notes = "카테고리 타입을 받아서 카테고리 이름을 조회하는 API")
    @ApiImplicitParam(name = "categoryType", value = "카테고리 타입", example = "fixed", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "해당 카테고리가 정상적으로 조회된 경우"),
            @ApiResponse(responseCode = "404", description = "회원 Id를 찾지 못한 경우")
    })
    @GetMapping("/category")
    public ResponseEntity<AssetCategoriesResponse> getCategory(@RequestParam String categoryType) {
        return ResponseEntity.ok(new AssetCategoriesResponse(assetService.getCategories(categoryType)));
    }

    @ApiOperation(value = "예산 설정", notes = "한달 예산 금액을 설정하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "한달 예산 금액이 정상적으로 설정된 경우"),
            @ApiResponse(responseCode = "404", description = "회원 Id를 찾지 못한 경우")
    })
    @PutMapping("/budget")
    public ResponseEntity<CreateBudgetResponse> addBudget(@RequestBody BudgetRequest request) {
        return ResponseEntity.ok(new CreateBudgetResponse(assetService.addBudget(request)));
    }

    @ApiOperation(value = "가계부 설정 카테고리 생성", notes = "카테고리 타입과 카테고리 이름을 입력받아 카테고리를 생성하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카테고리가 정상적으로 생성된 경우"),
            @ApiResponse(responseCode = "404", description = "회원 Id를 찾지 못한 경우")
    })
    @PostMapping("/category")
    public ResponseEntity<AssetCategoryResponse> addCategory(@RequestBody AssetCategoryRequest request) {
        return ResponseEntity.ok(new AssetCategoryResponse(assetService.addCategory(request)));
    }

    @ApiOperation(value = "가계부 설정 카테고리 삭제", notes = "카테고리 Id를 입력받아 삭제하는 API")
    @ApiImplicitParam(name = "categoryId", value = "카테고리 Id", example = "1", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "카테고리가 정상적으로 삭제된 경우"),
            @ApiResponse(responseCode = "404", description = "회원 Id OR 카테고리 Id를 찾지 못한 경우")
    })
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<Void> deleteCategoryName(@PathVariable Long categoryId) {
        assetService.deleteCategoryName(categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "지출 비율 설정", notes = "고정비, 변동비를 받아와서 지출 비율을 설정 하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "지츌 비율 설정이 성공적으로 저장된 경우"),
            @ApiResponse(responseCode = "404", description = "회원 Id를 찾지 못한 경우"),
            @ApiResponse(responseCode = "400", description = "고정비, 변동비의 합이 100%가 아닌 경우")
    })
    @PutMapping("/expenditure")
    public ResponseEntity<CreateExpenditureResponse> addExpenditure(@Validated @RequestBody ExpenditureRequest request) {
        return ResponseEntity.ok(new CreateExpenditureResponse(assetService.addExpenditure(request)));
    }

    @ApiOperation(value = "수익 지출 내역 추가", notes = "Request Body 값을 받아와서 수익 지출 내역을 추가하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "해당 수익 지출 내역을 정상적으로 추가한 경우"),
            @ApiResponse(responseCode = "404", description = "회원 Id를 찾지 못한 경우")
    })
    @PostMapping("/revenueExpenditure")
    public ResponseEntity<CreateRevenueExpenditureResponse> addRevenueExpenditure(@RequestBody CreateRevenueExpenditureRequest request) {
        return ResponseEntity.ok(new CreateRevenueExpenditureResponse(assetService.addRevenueExpenditure(request)));
    }

    @ApiOperation(value = "수익 지출 내역 조회", notes = "해당 년 월, page, size 를 입력받아 한달 수익 지출 내역을 조회하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "해당 수익 지출 내역을 정상적으로 조회한 경우"),
            @ApiResponse(responseCode = "404", description = "회원 Id OR 예산 금액 Id를 찾지 못한 경우")
    })
    @ApiImplicitParam(name = "month", value = "해당 년 월", example = "2022-04", required = true)
    @GetMapping("/revenueExpenditure")
    public ResponseEntity<RevenueExpenditureSumResponse> getRevenueExpenditures(@RequestParam String month, Pageable pageable) {
        return ResponseEntity.ok(assetService.findRevenueExpenditureByMonth(month, pageable));
    }

    @PutMapping("/asset-goal")
    public ResponseEntity<CreateAssetGoalResponse> addAssetGoal(@RequestBody CreateAssetGoalRequest request) {
        return ResponseEntity.ok(new CreateAssetGoalResponse(assetService.addAssetGoal(request)));
    }

}
