//package com.ssafy.hotplace.model.mapper;
//
//import java.sql.SQLException;
//import java.util.List;
//
//import com.ssafy.hotplace.model.HotplaceDto;
//import org.apache.ibatis.annotations.Mapper;
//
//@Mapper
//public interface HotPlaceMapper {
//
//    List<HotplaceDto> list() throws SQLException;
//    List<HotplaceDto> hotplaceTOP3() throws SQLException;
//    void write(HotplaceDto hotplaceDto) throws SQLException;
//    void modify(HotplaceDto hotplaceDto) throws SQLException;
//    void delete(int hotplaceNo) throws SQLException;
//
//    HotplaceDto getHotplace(int hotplaceNo) throws SQLException;
//
//    int registerFile(List<String> fileList) throws SQLException;
//
//
//}