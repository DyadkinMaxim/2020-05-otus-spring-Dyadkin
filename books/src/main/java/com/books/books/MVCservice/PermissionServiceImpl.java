package com.books.books.MVCservice;

import com.books.books.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private MutableAclService aclService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Transactional
    public void addPermissionForUser(Book book, Permission permission, String username) {
        final Sid sid = new PrincipalSid(username);
        addPermissionForSid(book, permission, sid);
    }

    private void addPermissionForSid(Book book, Permission permission, Sid sid) {
        final TransactionTemplate tt = new TransactionTemplate(transactionManager);

        tt.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                final ObjectIdentity oi = new ObjectIdentityImpl(book.getClass(), book.getId());

                MutableAcl acl = null;
                try {
                    acl = (MutableAcl) aclService.readAclById(oi);
                } catch (final NotFoundException nfe) {
                    acl = aclService.createAcl(oi);
                }

                acl.insertAce(acl.getEntries()
                        .size(), permission, sid, true);
                aclService.updateAcl(acl);
            }
        });
    }
}
