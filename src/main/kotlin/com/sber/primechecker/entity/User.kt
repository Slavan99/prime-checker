package com.sber.primechecker.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
data class User(
    @Id
    @Column(unique = true)
    var name: String? = null,

    var email: String? = null,
    var passwd: String? = null,

    var isActive: Boolean = false,

    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = [JoinColumn(name = "user_name")])
    @Enumerated(
        EnumType.STRING
    )
    private var roles: MutableSet<Role> = HashSet()
) : UserDetails {
    constructor() : this("", "", "", false, mutableSetOf())

    override fun getAuthorities(): Collection<GrantedAuthority?> = roles

    override fun getPassword(): String = passwd!!

    override fun getUsername(): String = name!!

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = isActive

    fun addRole(role: Role) = roles.add(role)

    fun removeRole(role: Role) = roles.remove(role)

    fun isAdmin() = roles.contains(Role.ADMIN)

}