package dbConnection;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DBConnection {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"spring.xml");
		MyDAO dao = context.getBean("myDAO", MyDAO.class);

		Circle circle = dao.getCircle(1);
		System.out.println(circle.getName());
		System.out.println(dao.getCircleCount());
		List<Circle> circle2 = dao.getCircles(2);
		for (Circle c: circle2){
			System.out.println("circle: "+c.getId()+" "+c.getName());
		}
		dao.insert2(2, "otherInsertAgain");
//		System.out.println("get circles: " + circle.getName() + circle.getId());
		System.out.println("get all circles: ");
		List<Circle> list = dao.getAllCircles();
		for (Circle c: list){
			System.out.println("circle: "+c.getId()+" "+c.getName());
		}
		
//		dao.createTriangleTable();
		//triple likhungi
		
		System.out.println("------success-----");
		
		NamedParaSup name = context.getBean("namedParaSup", NamedParaSup.class);
		System.out.println(name.getCircleCount());

	}

}
