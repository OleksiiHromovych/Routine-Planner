package android.hromovych.com.routineplanner.domain.repository.template_edit

import android.hromovych.com.routineplanner.domain.entity.Template
import android.hromovych.com.routineplanner.domain.usecase.UseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.map

class GetTemplateWithDoingsUseCase(
    private val templateEditRepository: TemplateEditRepository
): UseCase<Long, LiveData<Template>>() {

    override fun execute(parameters: Long): LiveData<Template> {
        return templateEditRepository.getTemplateWithDoings(parameters).map {
            it.apply {
                doings = doings.sortedBy { it.position }
            }
        }
    }

}