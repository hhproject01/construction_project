package edu.hhu.construction.validator;

import edu.hhu.construction.util.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 如何判断手机格式是否正确
 * ConstraintValidator<IsMobile, String> 注解类型；修饰字段类型
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

	private boolean required = false;

	//获取注解带来的值
	@Override
	public void initialize(IsMobile constraintAnnotation) {
		required = constraintAnnotation.required();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(required) {//required = true 是必须的
			return ValidatorUtil.isMobile(value);

		}else {//required = false 不是必须的
			if(StringUtils.isEmpty(value)) {//值为空
				return true;
			}else {//值不为空 判断格式
				return ValidatorUtil.isMobile(value);
			}
		}
	}

}
