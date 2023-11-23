package com.ssafy.hotplace.model;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class HotPlaceListDto {
    //	@ApiModelProperty(value = "글목록")
    private List<HotPlaceDto> hotplaces;
    //	@ApiModelProperty(value = "현재 페이지번호")
    private int currentPage;
    //	@ApiModelProperty(value = "전체 페이지 수")
    private int totalPageCount;
}
