package de.sveri.auth.validator

import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties


//@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PasswordsEqualConstraintValidator::class])
annotation class EqualFields(
        val message: String = "Fields dont match",
        val baseField: String,
        val matchField: String,
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)


class PasswordsEqualConstraintValidator : ConstraintValidator<EqualFields, Any> {
    private var baseField: String? = null
    private var matchField: String? = null

    override fun initialize(constraint: EqualFields) {
        baseField = constraint.baseField
        matchField = constraint.matchField
    }

    override fun isValid(candidate: Any, context: ConstraintValidatorContext): Boolean {
        val baseFieldValue = getFieldValue<String>(candidate, baseField)
        val matchFieldValue = getFieldValue<String>(candidate, matchField)
        return baseFieldValue.equals(matchFieldValue)
    }

    private fun <R : Any?> getFieldValue(instance: Any, fieldName: String?): R {
        val clazz = instance.javaClass.kotlin
        return clazz.declaredMemberProperties.first { it.name == fieldName }.get(instance) as R
    }
}