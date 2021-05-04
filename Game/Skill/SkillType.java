package Game.Skill;

public enum SkillType {

    BLINK(new Blink()), HEAL(new Blink());

    private Skill skill;

    SkillType(Skill skill) {
        this.skill = skill;
    }

    public Skill getSkill() {
        return this.skill;
    }

}
