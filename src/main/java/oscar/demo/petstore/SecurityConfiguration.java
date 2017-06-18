package oscar.demo.petstore;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private AuthSuccessHandler authSuccessHandler;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers("/js/**")
				.antMatchers("/img/**")
				.antMatchers("/css/**")
				.antMatchers("/partials/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()

				.authorizeRequests()

				.antMatchers("/", "/login", "/index.html", "/user").permitAll()

				.antMatchers(HttpMethod.GET, "/petstore/**").hasAuthority(Permission.READ.getAuthority())
				.antMatchers(HttpMethod.POST, "/petstore/**").hasAuthority(Permission.CREATE.getAuthority())
				.antMatchers(HttpMethod.DELETE, "/petstore/**").hasAuthority(Permission.DELETE.getAuthority())

				.anyRequest().authenticated()

				.and().formLogin().successHandler(authSuccessHandler)

				.and().httpBasic().disable()

				.logout().deleteCookies("JSESSIONID").permitAll()

				.and().rememberMe().disable()

				.sessionManagement().maximumSessions(1);

	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.inMemoryAuthentication()
				.withUser("user").password("p").authorities(Permission.USER_PERMISSIONS)
				.and()
				.withUser("admin").password("p").authorities(Permission.ADMIN_PERMISSIONS);
	}

	@Component
	public static class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
		private final ObjectMapper TOKEN_MAPPER = new ObjectMapper();

		@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
				Authentication authentication) throws IOException, ServletException {

			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			TOKEN_MAPPER.writeValue(response.getWriter(), authentication);
		}
	}
}
