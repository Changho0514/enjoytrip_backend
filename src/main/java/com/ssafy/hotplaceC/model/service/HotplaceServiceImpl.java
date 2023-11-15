//package com.ssafy.hotplace.model.service;
//
//import java.util.List;
//
//import com.ssafy.hotplace.model.HotplaceParamterDto;
//import com.ssafy.hotplace.model.mapper.HotPlaceMapper;
//import org.springframework.stereotype.Service;
//
//import com.ssafy.hotplace.model.HotplaceDto;
//import com.ssafy.hotplace.model.mapper.HotPlaceMapper;
//
//@Service
//public class HotplaceServiceImpl implements HotplaceService {
//
//    private HotPlaceMapper hotplaceMapper;
//
//    public HotplaceServiceImpl(HotPlaceMapper hotplaceMapper) {
//        super();
//        this.hotplaceMapper = hotplaceMapper;
//    }
//    @Override
//    public List<HotplaceDto> list(HotplaceParamterDto hotplaceParamterDto) throws Exception {
//        return null;
//    }
//
//    @Override
//    public List<HotplaceDto> hotplaceTOP3() throws Exception {
//        return hotplaceMapper.hotplaceTOP3();
//    }
//
//    @Override
//    public void write(HotplaceDto hotplaceDto) throws Exception {
//        hotplaceMapper.write(hotplaceDto);
//    }
//
//    @Override
//    public HotplaceDto getHotplace(int hotplaceNo) throws Exception {
//        return hotplaceMapper.getHotplace(hotplaceNo);
//    }
//
//    @Override
//    public void modify(HotplaceDto hotplaceDto) throws Exception {
//        hotplaceMapper.modify(hotplaceDto);
//
//    }
//
//    @Override
//    public void delete(int hotplaceNo) throws Exception {
//        hotplaceMapper.delete(hotplaceNo);
//    }
//
//}
