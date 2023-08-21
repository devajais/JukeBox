package com.crio.codingame.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.crio.codingame.dtos.UserRegistrationDto;
import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.ContestStatus;
import com.crio.codingame.entities.RegisterationStatus;
import com.crio.codingame.entities.ScoreOrder;
import com.crio.codingame.entities.User;
import com.crio.codingame.exceptions.ContestNotFoundException;
import com.crio.codingame.exceptions.InvalidOperationException;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.codingame.repositories.IContestRepository;
import com.crio.codingame.repositories.IUserRepository;

public class UserService implements IUserService {


    private final IUserRepository userRepository;
    private final IContestRepository contestRepository;

    public UserService(IUserRepository userRepository, IContestRepository contestRepository) {
        this.userRepository = userRepository;
        this.contestRepository = contestRepository;
    }
    // TODO: CRIO_TASK_MODULE_SERVICES
    // Create and store User into the repository.
    @Override
    public User create(String name) {
        User user = new User("1",name,0);
        userRepository.save(user);
     return user;
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Get All Users in Ascending Order w.r.t scores if ScoreOrder ASC.
    // Or
    // Get All Users in Descending Order w.r.t scores if ScoreOrder DESC.

    

    @Override
    public List<User> getAllUserScoreOrderWise(ScoreOrder scoreOrder){

        List<User> allUsers = userRepository.findAll();
        if(scoreOrder == ScoreOrder.ASC){
            return allUsers.stream().sorted(Comparator.comparing(User::getScore)).collect(Collectors.toList());
        }
        return allUsers.stream().sorted(Comparator.comparing(User::getScore).reversed()).collect(Collectors.toList());
    }

    @Override
    public UserRegistrationDto attendContest(String contestId, String userName) throws ContestNotFoundException, UserNotFoundException, InvalidOperationException {
        Contest contest = contestRepository.findById(contestId).orElseThrow(() -> new ContestNotFoundException("Cannot Attend Contest. Contest for given id:"+contestId+" not found!"));
        User user = userRepository.findByName(userName).orElseThrow(() -> new UserNotFoundException("Cannot Attend Contest. User for given name:"+ userName+" not found!"));
        if(contest.getContestStatus().equals(ContestStatus.IN_PROGRESS)){
            throw new InvalidOperationException("Cannot Attend Contest. Contest for given id:"+contestId+" is in progress!");
        }
        if(contest.getContestStatus().equals(ContestStatus.ENDED)){
            throw new InvalidOperationException("Cannot Attend Contest. Contest for given id:"+contestId+" is ended!");
        }
        if(user.checkIfContestExists(contest)){
            throw new InvalidOperationException("Cannot Attend Contest. Contest for given id:"+contestId+" is already registered!");
        }
        user.addContest(contest);
        userRepository.save(user);
        return new UserRegistrationDto(contest.getName(), user.getName(),RegisterationStatus.REGISTERED);
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Withdraw the user from the contest
    // Hint :- Refer Unit Testcases withdrawContest method

    @Override
    public UserRegistrationDto withdrawContest(String contestId, String userName) throws ContestNotFoundException, UserNotFoundException, InvalidOperationException {
        Optional<Contest> contest = contestRepository.findById(contestId);
        Optional<User> user = userRepository.findByName(userName);

        
        if(contest.isEmpty()){
            throw new ContestNotFoundException();
        }

        else if(user.isEmpty()){
            throw new UserNotFoundException();
        }
        else if(contest.get().getContestStatus()==ContestStatus.ENDED ){
            throw new InvalidOperationException();
        } //contest.get().getContestStatus() == ContestStatus.NOT_STARTED

        else if(!user.get().checkIfContestExists(contest.get())){
            throw new InvalidOperationException();
        }
        else{
            user.get().deleteContest(contest.get());
            userRepository.save(user.get());

            return new UserRegistrationDto(contest.get().getName(), userName, RegisterationStatus.NOT_REGISTERED);
        }
    }
    
}
