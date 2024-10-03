/*
 * Class: TagService
 * Description: Service for the Tag entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 18/09/2024
 * Last Updated: 18/09/2024
 */

package blomera.praceando.praceandoapipg.service;

import blomera.praceando.praceandoapipg.model.Tag;
import blomera.praceando.praceandoapipg.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    /**
     * @return uma lista de objetos Tag se existirem, ou null se não houver nenhuma tag.
     */
    public List<Tag> getTags() {
        List<Tag> tags = tagRepository.findAll();
        return tags.isEmpty() ? null : tags;
    }

    /**
     * @return tag pelo id, se ela existir, caso contrário, retorna null
     */
    public Tag getTagById(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        return tag.orElse(null);
    }

    /**
     * @return tag deletada.
     */
    public Tag deleteTagById(Long id) {
        Tag tag = getTagById(id);
        if (tag != null) tagRepository.deleteById(id);
        return tag;
    }

    /**
     * @return tag inserida.
     */
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    /**
     * Atualiza os dados de uma tag.
     * @param id Id da tag a ser atualizada.
     * @param tag Tag com os novos dados.
     * @return tag atualizada ou nula, caso ela não exista
     */
    public Tag updateTag(Long id, Tag tag) {
        Tag existingTag = getTagById(id);
        if (existingTag != null) {
            existingTag.setNmTag(tag.getNmTag());
            existingTag.setDsCategoria(tag.getDsCategoria());
            existingTag.setDtAtualizacao(tag.getDtAtualizacao());
            return tagRepository.save(existingTag);
        }
        return null;
    }
}
