import java.util.ArrayList;

import ky.CollisionEntity;
import ky.Vector2D;

public class testCharacter extends Character {

    public testCharacter () {
        super(150, 250, 1000, "characterName", 3, 1);
        maxHP = 2000;
        HP = 2000;
        // setIcon(new Asset("assets/Characters/testcharacter/icon.png", new Vector2D(0, 0), 3));
        /* 
        characterAnimation = new AnimationAsset(images, position, animationTime, layer);
        characterAnimation.setVisible(true);
        add(characterAnimation);
        */
    }

    public void onCollision(CollisionEntity collidingEntity) {
        if (collidingEntity.getName().equals("ground")) {
            setVel(new Vector2D(0, 0));
            // setPos(new Vector2D(getX(), collidingEntity.getYCollisionBox().getY()-getCollisionBox().height/2));
            canJump = true;
        }
    }

    @Override
    protected void basicAttack() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void basicAbility() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void ultimate() {
        // TODO Auto-generated method stub
        
    }
    
}
