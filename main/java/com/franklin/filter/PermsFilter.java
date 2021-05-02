package com.franklin.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class PermsFilter extends PermissionsAuthorizationFilter {
    @Override
    public boolean isAccessAllowed (ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        Subject subject = this.getSubject(request, response);
        String[] perms = (String[]) mappedValue;

        boolean isPermitted = true;
        if (perms != null && perms.length > 0) {
            if (perms.length == 1) {
                if (!isOneofPermitted(perms[0],subject)) {
                    isPermitted = false;
                }
            } else {
                if (!isAllPermitted(perms,subject)) {
                    isPermitted = false;
                }
            }
        }
        return isPermitted;
    }

    private boolean isAllPermitted(String[] permsArr,Subject subject){
        boolean isPermitted = true;
        for(String perms : permsArr){
            if (!isOneofPermitted(perms,subject)){
                isPermitted = false;
            }
        }
        return isPermitted;
    }

    private boolean isOneofPermitted(String permStr,Subject subject) {
        boolean isPermitted =false;
        String[] permsArr = permStr.split("\\|");
        if(permsArr.length > 0){
            for(String perms : permsArr){
                if(subject.isPermitted(perms)){
                    isPermitted = true;
                }
            }
        }
        return isPermitted;
    }
}

