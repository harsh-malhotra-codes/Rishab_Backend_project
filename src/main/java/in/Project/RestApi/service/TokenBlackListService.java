package in.Project.RestApi.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TokenBlackListService {
    private Set<String> blacklist = new HashSet<>();

    public void addTokenToBlackList(String token) {
        blacklist.add(token);
    }

    public boolean isTokenBlackList(String token) {
        return blacklist.contains(token);
    }
}
