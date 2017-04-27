

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'org.example.SecUser'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'org.example.SecUserSecRole'
grails.plugin.springsecurity.authority.className = 'org.example.SecRole'
grails.plugin.springsecurity.successHandler.defaultTargetUrl = '/gateWay/index'
grails.plugin.springsecurity.auth.loginFormUrl = '/login/auth'
grails.plugin.springsecurity.failureHandler.defaultFailureUrl = '/gateWay/index/?login_error=1'
grails.plugin.springsecurity.logout.postOnly = false

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	[pattern: '/',               access: ['permitAll']],
	[pattern: '/error',          access: ['permitAll']],
	[pattern: '/index',          access: ['permitAll']],
	[pattern: '/index.gsp',      access: ['permitAll']],
	[pattern: '/shutdown',       access: ['permitAll']],
	[pattern: '/assets/**',      access: ['permitAll']],
    [pattern: '/assets/fonts/**',access: ['permitAll']],
    [pattern: '/assets/images/**',access: ['permitAll']],
    [pattern: '/assets/javascripts/**',access: ['permitAll']],
    [pattern: '/assets/stylesheets/**',access: ['permitAll']],
	[pattern: '/**/js/**',       access: ['permitAll']],
	[pattern: '/**/css/**',      access: ['permitAll']],
	[pattern: '/**/images/**',   access: ['permitAll']],
	[pattern: '/**/favicon.ico', access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
	[pattern: '/assets/**',      filters: 'none'],
	[pattern: '/**/js/**',       filters: 'none'],
	[pattern: '/**/css/**',      filters: 'none'],
	[pattern: '/**/images/**',   filters: 'none'],
	[pattern: '/**/favicon.ico', filters: 'none'],
	[pattern: '/**',             filters: 'JOINED_FILTERS']
]

