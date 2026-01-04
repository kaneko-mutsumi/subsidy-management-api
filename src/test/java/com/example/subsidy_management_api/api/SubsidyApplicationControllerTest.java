package com.example.subsidy_management_api.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.subsidy_management_api.exception.NotFoundException;
import com.example.subsidy_management_api.service.SubsidyApplicationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SubsidyApplicationController.class)
class SubsidyApplicationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private SubsidyApplicationService service;


  @Test
  void create_returns201_whenValid() throws Exception {
    // service.create(...) が成功する前提（戻り値がある場合は適宜合わせる）
    // 例：createがSubsidyApplicationCreateResponseを返すなら、その型でthenReturnする
    Mockito.when(service.create(any())).thenReturn(
        // ここはあなたの実装のレスポンスDTOに合わせてください
        // new SubsidyApplicationCreateResponse(1L, 10L)
        null
    );

    String body = """
        {
          "applicant": {
            "fullName": "佐藤花子",
            "email": "hanako@example.com"
          },
          "application": {
            "applicationDate": "2026-01-05",
            "amountRequested": 150000,
            "status": "APPLIED"
          }
        }
        """;

    mockMvc.perform(post("/subsidy-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isCreated());
  }

  @Test
  void create_returns400_whenInvalid() throws Exception {
    // fullName が空 → validation で 400 を期待（あなたのDTOに@NotBlankがある前提）
    String body = """
        {
          "applicant": {
            "fullName": "",
            "email": "hanako@example.com"
          },
          "application": {
            "applicationDate": "2026-01-05",
            "amountRequested": 150000,
            "status": "APPLIED"
          }
        }
        """;

    mockMvc.perform(post("/subsidy-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andDo(org.springframework.test.web.servlet.result.MockMvcResultHandlers.print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void detail_returns404_whenNotFound() throws Exception {
    Mockito.when(service.findDetail(eq(999L)))
        .thenThrow(new NotFoundException("not found"));

    mockMvc.perform(get("/subsidy-applications/999"))
        .andExpect(status().isNotFound());
  }

  @Test
  void delete_returns404_whenNotFound() throws Exception {
    Mockito.doThrow(new NotFoundException("not found"))
        .when(service).delete(eq(999L));

    mockMvc.perform(delete("/subsidy-applications/999"))
        .andExpect(status().isNotFound());
  }
}
