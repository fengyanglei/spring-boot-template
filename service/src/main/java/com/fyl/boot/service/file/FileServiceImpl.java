package com.fyl.boot.service.file;

import com.fyl.boot.api.file.FileService;
import com.fyl.boot.common.param.FileResourcesParam;
import com.fyl.boot.common.response.FileResourcesResponse;
import com.fyl.boot.common.response.Response;
import com.fyl.boot.dao.pojo.FileResources;
import com.fyl.boot.service.BaseServiceImpl;
import com.fyl.boot.util.BeanUtils;
import com.fyl.boot.util.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl extends BaseServiceImpl<FileResources> implements FileService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<FileResourcesResponse> list(String dir) {
        FileResources param = new FileResources();
        param.setDir(dir);
        List<FileResources> list = this.findAll(param);
        if (ObjectUtils.isBlank(list)) {
            return null;
        }
        return BeanUtils.copyList(list, FileResourcesResponse.class);
    }

    @Override
    public Response save(FileResourcesParam param) {
        FileResources obj = BeanUtils.copy(param, FileResources.class);
        this.insert(obj);
        return new Response();
    }
}
