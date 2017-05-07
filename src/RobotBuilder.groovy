

/**
 * Created by Ramisetti on 5/7/17.
 */

class Test {
    public static void main(String[] args) {
        def  builder = new RobotBuilder()
        def robot = builder.robot('iRobot') {
            forward(dist: 20)
            left(rotation: 90)
            forward(speed: 10, duration: 5)
        }
        robot.go()
    }
}

class RobotBuilder extends FactoryBuilderSupport {

    {
        registerFactory('robot', new RobotFactory())
        registerFactory('forward', new ForwardMoveFactory())
        registerFactory('left', new LeftTurnFactory())

    }
}


class Robot {

    String name
    List movements = []

    def go() {
        println "Robot $name operating..."
        movements.each {
            println it
        }
    }

}

class ForwardMove {
    def dist
    String toString() { "moving distance $dist" }

}

class LeftTurn {
    def rotation
    String toString() { "Turn Left.... $rotation degrees" }
}


class RobotFactory extends AbstractFactory {

    @Override
    Object newInstance(FactoryBuilderSupport factoryBuilderSupport, name, value, Map attributes) {
        new Robot(name: value)
    }

    void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        parent.movements << child
    }

}

class ForwardMoveFactory extends  AbstractFactory {

    @Override
    boolean isLeaf() {
        true
    }

    @Override
    Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object o, Object o1, Map map) throws InstantiationException, IllegalAccessException {
        new ForwardMove()
    }

    @Override
    boolean onHandleNodeAttributes(FactoryBuilderSupport builder, Object node, Map attributes) {
        if (attributes.speed && attributes.duration) {
            node.dist = attributes.speed * attributes.duration
            attributes.remove('speed')
            attributes.remove('duration')
        }
        true
    }
}

class LeftTurnFactory extends AbstractFactory {

    @Override
    boolean isLeaf() {
        true
    }

    @Override
    Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object o, Object o1, Map map) throws InstantiationException, IllegalAccessException {
        new LeftTurn()
    }
}
