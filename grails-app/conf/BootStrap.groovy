import com.startup.inventory.CategoryType
import com.startup.inventory.ProductItem
import com.startup.inventory.Status

import com.startup.inventory.uma.Role
import com.startup.inventory.uma.User
import com.startup.inventory.uma.UserRole

class BootStrap {

    def init = { servletContext ->
        createUserWithRole()
        createCategory()
//        createSubCategory()
       // createItem()
    }

    void createCategory(){
        CategoryType.findByName('Battery')?:new CategoryType(name: 'Battery',description: 'Battery only', status:Status.ACTIVE, priority:3).save()
        CategoryType.findByName('Solar Panel')?:new CategoryType(name: 'Solar Panel',description: 'Solar Panel only', status:Status.ACTIVE, priority:1).save()
        CategoryType.findByName('Bulb')?:new CategoryType(name: 'Bulb',description: 'Bulb only', status:Status.ACTIVE, priority:2).save()
        CategoryType.findByName('Cable')?:new CategoryType(name: 'Cable',description: 'Cable only', status:Status.ACTIVE, priority:4).save(flush: true)
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