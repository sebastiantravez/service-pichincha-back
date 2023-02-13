package com.service.pichincha.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountReportDTO {
    private String fullName;
    private String dni;
    private String accountType;
    private String accountNumber;
    private BigDecimal initialAmount;
    private String initDate;
    private String endDate;
    private String status;
    private List<AccountReportDetailDTO> accountReportDetail;
}
