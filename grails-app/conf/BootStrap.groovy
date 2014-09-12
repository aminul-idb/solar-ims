import com.startup.inventory.CategoryType
import com.startup.inventory.ProductItem
import com.startup.inventory.Status

import com.startup.inventory.uma.Role
import com.startup.inventory.uma.User
import com.startup.inventory.uma.UserRole

class BootStrap {

    def init = { servletContext ->
        createUserWithRole()
//        createCategory()
//        createSubCategory()
       // createItem()
    }

    void createCategory(){
        for(int i=0; i<30; i++){
            CategoryType.findByName('Battery'+[i])?:new CategoryType(name: 'Battery'+[i],description: 'Battery only', status:Status.ACTIVE, priority:3+i).save(flush: true)
            CategoryType.findByName('Solar Panel'+[i])?:new CategoryType(name: 'Solar Panel'+[i],description: 'Solar Panel only', status:Status.ACTIVE, priority:1+i).save(flush: true)
            CategoryType.findByName('Bulb'+[i])?:new CategoryType(name: 'Bulb'+[i],description: 'Bulb only', status:Status.ACTIVE, priority:2+i).save(flush: true)
            CategoryType.findByName('Cable'+[i])?:new CategoryType(name: 'Cable'+[i],description: 'Cable only', status:Status.ACTIVE, priority:4).save(flush: true)
        }
    }

    void createItem(){
        ProductItem.findByName('Battery A 11')?:new ProductItem(name: 'Battery A 11',description: 'Battery A 11 only', status: Status.ACTIVE).save()
        ProductItem.findByName('Battery A 12')?:new ProductItem(name: 'Battery A 12',description: 'Battery A 12 only', status: Status.ACTIVE).save()
        ProductItem.findByName('Solar Panel A 11')?:new ProductItem(name: 'Solar Panel A 12',description: 'Solar Panel A 12 only', status: Status.ACTIVE).save()
        ProductItem.findByName('Solar Panel A 12')?:new ProductItem(name: 'Solar Panel A 12',description: 'Solar Panel A 12 only', status: Status.ACTIVE).save()
        ProductItem.findByName('Bulb A 11')?:new ProductItem(name: 'Bulb A 11',description: 'Bulb A 11 only', status: Status.ACTIVE).save()
        ProductItem.findByName('Bulb B 12')?:new ProductItem(name: 'Bulb B 12',description: 'Bulb B 12 only', status: Status.ACTIVE).save(flush: true)
    }

    void createUserWithRole() {

        Role superAdmin = Role.findByAuthority("ROLE_SUPER_ADMIN")
        if (!superAdmin) {
            superAdmin = new Role(authority: 'ROLE_SUPER_ADMIN').save()
        }
        Role admin = Role.findByAuthority("ROLE_ADMIN")

        User superAdminUser = User.findByUsername('admin')
        if (!superAdminUser) {
            superAdminUser = new User(username: 'admin', password: 'password', enabled: true, accountExpired: false, accountLocked: false, passwordExpired: false)
            superAdminUser.save(flush: true)

            new UserRole(user: superAdminUser, role: superAdmin).save(flush: true)
        }

        def destroy = {
        }
    }
}