package Test.Test.service;

import Test.Test.dao.DetailUsersRepository;
import Test.Test.dao.JobsRepository;
import Test.Test.dao.UsersRepository;
import Test.Test.dto.Users.*;
import Test.Test.entity.DetailUsers;
import Test.Test.entity.Jobs;
import Test.Test.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private DetailUsersRepository detailUsersRepository;

    @Autowired
    private JobsRepository jobsRepository;

    public InfoUsersDetailDTO getUsers(){
        InfoUsersDetailDTO dto = new InfoUsersDetailDTO();
        dto.setStatus(true);
        var dtos = usersRepository.getAllUser();
        for(var userDto : dtos){
            userDto.setDetail(detailUsersRepository.getUserDetail(userDto.getId()));
            userDto.setJobs(jobsRepository.getJob(userDto.getId()));
        }
        dto.setData(dtos);
        return dto;
    }

    public InfoOneUserDetailDTO getOneUser(Long id){
        InfoOneUserDetailDTO dto = new InfoOneUserDetailDTO();
        UserDataDetailDTO userDto = new UserDataDetailDTO();
        dto.setStatus(true);
        Users user = usersRepository.findById(id).get();
        userDto.setId(id);
        userDto.setUsername(user.getUsername());
        userDto.setDetail(detailUsersRepository.getUserDetail(id));
        userDto.setJobs(jobsRepository.getJob(id));
        userDto.setCreated_by(user.getCreatedBy());
        userDto.setCreated_at(user.getCreatedAt());
        userDto.setUpdate_by(user.getUpdateBy());
        userDto.setUpdate_at(user.getUpdateAt());
        userDto.setDelete_by(user.getDeleteBy());
        userDto.setUpdate_at(user.getDeleteAt());
        dto.setData(userDto);
        return dto;
    }

    public DetailUsers saveDetailUser(UpsertUserDetailDTO dto){
        DetailUsers entity = new DetailUsers();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        return entity;
    }

    public Jobs saveJobUser(UpsertJobDTO dto){
        Jobs entity = new Jobs();
        entity.setName(dto.getName());
        entity.setStartAt(dto.getStartAt());
        entity.setEndAt(dto.getEndAt());
        return entity;
    }
}
