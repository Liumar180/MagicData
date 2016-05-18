package com.integrity.dataSmart.test;

import java.util.Iterator;

import org.apache.commons.configuration.BaseConfiguration;

import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author liuBf qq数据录入
 */
public class QQ {
	public static void main(String[] args) {
		BaseConfiguration baseConfiguration = new BaseConfiguration();
		baseConfiguration.setProperty("storage.backend", "hbase");
		baseConfiguration.setProperty("storage.hostname", "192.168.40.10");
		baseConfiguration.setProperty("storage.tablename", "titan");
		System.out.println("Open Data");
		TitanGraph graph = TitanFactory.open(baseConfiguration);
		System.out.println("数据库已连接");

		String domain = "QQ";
		// get(Fields.In, "domain").getString(r);//QQ
		String numid = "1151694121";
		// get(Fields.In, "numid ").getString(r);//QQ号
		String friendNum = "120";
		// get(Fields.In, "friendNum ").getString(r);//好友QQ号

		String nickname = "friends";
		// get(Fields.In,"nickname").getString(r);//好友备注

		String relationtype = "QQfriends";
		// get(Fields.In, "relationtype").getString(r);//关系类型

		String name = "";
		// get(Fields.In, "name").getString(r);//姓名
		String idcard = "";
		// get(Fields.In, "idcard").getString(r);//身份证号
		String country = "";
		// get(Fields.In, "country").getString(r);//国家

		Object QqIte = null;
		if (numid != null && !"".equals(numid)) {
			QqIte = graph.query().has("numid", numid).vertices();// 通过qq号查询
		}

		Vertex tmp = null;
		boolean t = false;
		if (QqIte != null) {
			t = ((Iterable) QqIte).iterator().hasNext();
		}
		while (t) {
			tmp = (Vertex) ((Iterable) QqIte).iterator().next();
			break;
		}
		if (tmp != null) {
			Vertex person = null;
			try {
				person = (Vertex) tmp.query().labels(new String[] { "own" })
						.vertices().iterator().next();
			} catch (java.util.NoSuchElementException e) {
				e.printStackTrace();
				// putRow(data.outputRowMeta, r);
				// return true;
			}
			if (person != null) {
				if (name != null && !name.equals("")) {
					Object names = person.getProperty("name");
					if (names == null || names.equals("")) {
						person.setProperty("name", name);
					}
				}
				if (idcard != null && !idcard.equals("")) {
					Object idcards = person.getProperty("idcard");
					if (idcards == null || idcards.equals("")) {
						person.setProperty("idcard", idcard);
					}
				}
				if (country != null && !country.equals("")) {
					Object countrys = person.getProperty("country");
					if (countrys == null || countrys.equals("")) {
						person.setProperty("country", country);
					}
				}
				Object childs = person.query().labels(new String[] { "own" })
						.vertices();
				boolean exist = false;// 当前事件（QQ）

				for (Iterator iterator = ((Iterable) childs).iterator(); iterator
						.hasNext();) {

					Vertex v = (Vertex) iterator.next();
					if (v != null) {
						if ("IM".equals(v.getProperty("type"))) {
							if (numid != null && !numid.equals("")
									&& domain != null && !"".equals(domain)) {
								if (v.getProperty("domain").equals(domain)
										&& v.getProperty("numid").equals(numid)) {
									// 如果domain,numid都相等的话，则去查看对象属性有没有更新，如果有更新则更新数据库中对象
									exist = true;
								}

							}

						}
					}

				}
				if (!exist) {
					Vertex qqEveve = null;
					if (numid != null && !"".equals(numid)) {
						Vertex Qq2 = graph.addVertex(null);
						Qq2.setProperty("type", "IM");
						if (domain != null && !"".equals(domain)) {
							Qq2.setProperty("domain", domain);
						}
						if (numid != null && !"".equals(numid)) {
							Qq2.setProperty("numid", numid);
						}
						graph.addEdge(null, person, Qq2, "own");
					}

					if (friendNum != null && !friendNum.equals("")) {

						Iterable personto = graph.query()
								.has("numid", friendNum).vertices();
						Iterator pp = personto.iterator();
						if (pp.hasNext()) {
							Vertex tmpto = (Vertex) pp.next();
							for (Iterator iterator1 = personto.iterator(); iterator1
									.hasNext();) {
								Vertex v1 = (Vertex) iterator1.next();

								if (friendNum != null && !"".equals(friendNum)) {
									if (v1.getProperty("type").equals("IM")
											&& v1.getProperty("numid").equals(
													friendNum)) {
										// 存qq 和 当前好友
										Vertex pto = null;
										try {
											pto = (Vertex) tmpto
													.query()
													.labels(new String[] { "own" })
													.vertices().iterator()
													.next();
										} catch (java.util.NoSuchElementException e) {
											e.printStackTrace();
										}
										if (pto != null) {
											boolean isTrue = true;
											Iterator coh = person
													.query()
													.labels(new String[] { "relational" })
													.vertices().iterator();
											while (coh.hasNext()) {
												Vertex PP = (Vertex) coh.next();
												if (PP == pto) {
													System.out
															.println("存在relational边");
													isTrue = false;
												}
											}
											if (isTrue) {
												Edge s = graph.addEdge(null,
														person, pto,
														"relational");
												s.setProperty("relationtype",
														relationtype);
												if (nickname != null
														&& !nickname.equals("")) {
													s.setProperty("nickname",
															nickname);
												}
											}

										} else {
											Vertex ptoF = graph.addVertex(null);
											ptoF.setProperty("type", "Person");

											graph.addEdge(null, ptoF, v1, "own");

											Edge s = graph.addEdge(null,
													person, ptoF, "relational");
											s.setProperty("relationtype",
													relationtype);
											if (nickname != null
													&& !nickname.equals("")) {
												s.setProperty("nickname",
														nickname);
											}

										}

									} else if (v1.getProperty("type").equals(
											"IM")
											&& !v1.getProperty("numid").equals(
													friendNum)) {
										Vertex pto = null;
										try {
											pto = (Vertex) tmpto
													.query()
													.labels(new String[] { "own" })
													.vertices().iterator()
													.next();
										} catch (java.util.NoSuchElementException e) {
											e.printStackTrace();
										}
										if (pto != null) {
											qqEveve = graph.addVertex(null);
											qqEveve.setProperty("type", "IM");
											qqEveve.setProperty("domain",
													domain);
											qqEveve.setProperty("numid",
													friendNum);
											graph.addEdge(null, pto, qqEveve,
													"own");

											boolean isTrue = true;
											Iterator coh = person
													.query()
													.labels(new String[] { "relational" })
													.vertices().iterator();
											while (coh.hasNext()) {
												Vertex PP = (Vertex) coh.next();
												if (PP == pto) {
													System.out
															.println("存在relational边");
													isTrue = false;
												}
											}
											if (isTrue) {
												Edge s = graph.addEdge(null,
														person, pto,
														"relational");
												s.setProperty("relationtype",
														relationtype);
												if (nickname != null
														&& !nickname.equals("")) {
													s.setProperty("nickname",
															nickname);
												}
											}

										}

									}
								}

							}
						} else {

							Vertex p = graph.addVertex(null);
							p.setProperty("type", "Person");

							if (friendNum != null && !friendNum.equals("")) {

								qqEveve = graph.addVertex(null);
								qqEveve.setProperty("type", "IM");
								qqEveve.setProperty("domain", domain);
								qqEveve.setProperty("numid", friendNum);
								graph.addEdge(null, p, qqEveve, "own");

								Edge s = graph.addEdge(null, person, p,
										"relational");
								s.setProperty("relationtype", relationtype);
								if (nickname != null && !nickname.equals("")) {
									s.setProperty("nickname", nickname);
								}

							}
						}
					}
				} else {

					Vertex qqEveve = null;

					if (friendNum != null && !friendNum.equals("")) {

						Iterable personto = graph.query()
								.has("numid", friendNum).vertices();
						Iterator fg = personto.iterator();
						if (fg.hasNext()) {
							Vertex tmpto = (Vertex) fg.next();
							for (Iterator iterator1 = personto.iterator(); iterator1
									.hasNext();) {
								Vertex v1 = (Vertex) iterator1.next();

								if (friendNum != null && !"".equals(friendNum)) {
									if (v1.getProperty("type").equals("IM")
											&& v1.getProperty("numid").equals(
													friendNum)) {
										Vertex pto = null;
										try {
											pto = (Vertex) tmpto
													.query()
													.labels(new String[] { "own" })
													.vertices().iterator()
													.next();
										} catch (java.util.NoSuchElementException e) {
											e.printStackTrace();
										}
										if (pto != null) {
											boolean isTrue = true;
											Iterator coh = person
													.query()
													.labels(new String[] { "relational" })
													.vertices().iterator();
											while (coh.hasNext()) {
												Vertex PP = (Vertex) coh.next();
												if (PP == pto) {
													System.out
															.println("存在relational边");
													isTrue = false;
												}
											}
											if (isTrue) {
												Edge s = graph.addEdge(null,
														person, pto,
														"relational");
												s.setProperty("relationtype",
														relationtype);
												if (nickname != null
														&& !nickname.equals("")) {
													s.setProperty("nickname",
															nickname);
												}
											}

										} else {

											Vertex ptoF = graph.addVertex(null);
											ptoF.setProperty("type", "Person");

											graph.addEdge(null, ptoF, v1, "own");

											Edge s = graph.addEdge(null,
													person, ptoF, "relational");
											s.setProperty("relationtype",
													relationtype);
											if (nickname != null
													&& !nickname.equals("")) {
												s.setProperty("nickname",
														nickname);
											}

										}

									} else if (v1.getProperty("type").equals(
											"IM")
											&& !v1.getProperty("numid").equals(
													friendNum)) {
										Vertex ptos = null;
										try {
											ptos = (Vertex) tmpto
													.query()
													.labels(new String[] { "own" })
													.vertices().iterator()
													.next();
										} catch (java.util.NoSuchElementException e) {
											e.printStackTrace();
										}
										if (ptos != null) {
											qqEveve = graph.addVertex(null);
											qqEveve.setProperty("type", "IM");
											qqEveve.setProperty("domain",
													domain);
											qqEveve.setProperty("numid",
													friendNum);
											graph.addEdge(null, ptos, qqEveve,
													"own");

											Edge s = graph.addEdge(null,
													person, ptos, "relational");
											s.setProperty("relationtype",
													relationtype);
											if (nickname != null
													&& !nickname.equals("")) {
												s.setProperty("nickname",
														nickname);
											}

										}
									}
								}

							}
						} else {

							Vertex p = graph.addVertex(null);
							p.setProperty("type", "Person");

							if (friendNum != null && !friendNum.equals("")) {
								qqEveve = graph.addVertex(null);
								qqEveve.setProperty("type", "IM");
								qqEveve.setProperty("domain", domain);
								qqEveve.setProperty("numid", friendNum);
								graph.addEdge(null, p, qqEveve, "own");

								Edge s = graph.addEdge(null, person, p,
										"relational");
								s.setProperty("relationtype", relationtype);
								if (nickname != null && !nickname.equals("")) {
									s.setProperty("nickname", nickname);
								}

							}
						}
					}
				}
			} else {

				Vertex person3 = graph.addVertex(null);// 人物对象
				person3.setProperty("type", "Person");
				if (name != null && !name.equals("")) {
					person3.setProperty("name", name);
				}
				if (idcard != null && !idcard.equals("")) {
					person3.setProperty("idcard", idcard);
				}
				if (country != null && !country.equals("")) {
					person3.setProperty("country", country);
				}
				graph.addEdge(null, person3, tmp, "own");

				Object childs = person3.query().labels(new String[] { "own" })
						.vertices();
				boolean exist = false;// 当前事件（QQ）

				for (Iterator iterator = ((Iterable) childs).iterator(); iterator
						.hasNext();) {

					Vertex v = (Vertex) iterator.next();
					if (v != null) {
						if ("IM".equals(v.getProperty("type"))) {
							if (numid != null && !numid.equals("")
									&& domain != null && !"".equals(domain)) {
								if (v.getProperty("domain").equals(domain)
										&& v.getProperty("numid").equals(numid)) {
									// 如果domain,numid都相等的话，则去查看对象属性有没有更新，如果有更新则更新数据库中对象
									exist = true;
								}

							}

						}
					}

				}
				if (!exist) {
					Vertex qqEveve = null;
					if (numid != null && !"".equals(numid)) {
						Vertex Qq2 = graph.addVertex(null);
						Qq2.setProperty("type", "IM");
						if (domain != null && !"".equals(domain)) {
							Qq2.setProperty("domain", domain);
						}
						if (numid != null && !"".equals(numid)) {
							Qq2.setProperty("numid", numid);
						}
						graph.addEdge(null, person3, Qq2, "own");
					}

					if (friendNum != null && !friendNum.equals("")) {

						Iterable personto = graph.query()
								.has("numid", friendNum).vertices();
						Iterator pp = personto.iterator();
						if (pp.hasNext()) {
							Vertex tmpto = (Vertex) pp.next();
							for (Iterator iterator1 = personto.iterator(); iterator1
									.hasNext();) {
								Vertex v1 = (Vertex) iterator1.next();

								if (friendNum != null && !"".equals(friendNum)) {
									if (v1.getProperty("type").equals("IM")
											&& v1.getProperty("numid").equals(
													friendNum)) {
										// 存qq 和 当前好友
										Vertex pto = null;
										try {
											pto = (Vertex) tmpto
													.query()
													.labels(new String[] { "own" })
													.vertices().iterator()
													.next();
										} catch (java.util.NoSuchElementException e) {
											e.printStackTrace();
										}
										if (pto != null) {
											boolean isTrue = true;
											Iterator coh = person3
													.query()
													.labels(new String[] { "relational" })
													.vertices().iterator();
											while (coh.hasNext()) {
												Vertex PP = (Vertex) coh.next();
												if (PP == pto) {
													System.out
															.println("存在relational边");
													isTrue = false;
												}
											}
											if (isTrue) {
												Edge s = graph.addEdge(null,
														person3, pto,
														"relational");
												s.setProperty("relationtype",
														relationtype);
												if (nickname != null
														&& !nickname.equals("")) {
													s.setProperty("nickname",
															nickname);
												}
											}

										} else {
											Vertex ptoF = graph.addVertex(null);
											ptoF.setProperty("type", "Person");

											graph.addEdge(null, ptoF, v1, "own");

											Edge s = graph
													.addEdge(null, person3,
															ptoF, "relational");
											s.setProperty("relationtype",
													relationtype);
											if (nickname != null
													&& !nickname.equals("")) {
												s.setProperty("nickname",
														nickname);
											}

										}

									} else if (v1.getProperty("type").equals(
											"IM")
											&& !v1.getProperty("numid").equals(
													friendNum)) {
										Vertex pto = null;
										try {
											pto = (Vertex) tmpto
													.query()
													.labels(new String[] { "own" })
													.vertices().iterator()
													.next();
										} catch (java.util.NoSuchElementException e) {
											e.printStackTrace();
										}
										if (pto != null) {
											qqEveve = graph.addVertex(null);
											qqEveve.setProperty("type", "IM");
											qqEveve.setProperty("domain",
													domain);
											qqEveve.setProperty("numid",
													friendNum);
											graph.addEdge(null, pto, qqEveve,
													"own");

											boolean isTrue = true;
											Iterator coh = person3
													.query()
													.labels(new String[] { "relational" })
													.vertices().iterator();
											while (coh.hasNext()) {
												Vertex PP = (Vertex) coh.next();
												if (PP == pto) {
													System.out
															.println("存在relational边");
													isTrue = false;
												}
											}
											if (isTrue) {
												Edge s = graph.addEdge(null,
														person3, pto,
														"relational");
												s.setProperty("relationtype",
														relationtype);
												if (nickname != null
														&& !nickname.equals("")) {
													s.setProperty("nickname",
															nickname);
												}
											}

										}

									}
								}

							}
						} else {

							Vertex p = graph.addVertex(null);
							p.setProperty("type", "Person");

							if (friendNum != null && !friendNum.equals("")) {

								qqEveve = graph.addVertex(null);
								qqEveve.setProperty("type", "IM");
								qqEveve.setProperty("domain", domain);
								qqEveve.setProperty("numid", friendNum);
								graph.addEdge(null, p, qqEveve, "own");

								Edge s = graph.addEdge(null, person3, p,
										"relational");
								s.setProperty("relationtype", relationtype);
								if (nickname != null && !nickname.equals("")) {
									s.setProperty("nickname", nickname);
								}

							}
						}
					}
				} else {

					Vertex qqEveve = null;

					if (friendNum != null && !friendNum.equals("")) {

						Iterable personto = graph.query()
								.has("numid", friendNum).vertices();
						Iterator fg = personto.iterator();
						if (fg.hasNext()) {
							Vertex tmpto = (Vertex) fg.next();
							for (Iterator iterator1 = personto.iterator(); iterator1
									.hasNext();) {
								Vertex v1 = (Vertex) iterator1.next();

								if (friendNum != null && !"".equals(friendNum)) {
									if (v1.getProperty("type").equals("IM")
											&& v1.getProperty("numid").equals(
													friendNum)) {
										Vertex pto = null;
										try {
											pto = (Vertex) tmpto
													.query()
													.labels(new String[] { "own" })
													.vertices().iterator()
													.next();
										} catch (java.util.NoSuchElementException e) {
											e.printStackTrace();
										}
										if (pto != null) {
											boolean isTrue = true;
											Iterator coh = person3
													.query()
													.labels(new String[] { "relational" })
													.vertices().iterator();
											while (coh.hasNext()) {
												Vertex PP = (Vertex) coh.next();
												if (PP == pto) {
													System.out
															.println("存在relational边");
													isTrue = false;
												}
											}
											if (isTrue) {
												Edge s = graph.addEdge(null,
														person3, pto,
														"relational");
												s.setProperty("relationtype",
														relationtype);
												if (nickname != null
														&& !nickname.equals("")) {
													s.setProperty("nickname",
															nickname);
												}
											}

										} else {

											Vertex ptoF = graph.addVertex(null);
											ptoF.setProperty("type", "Person");

											graph.addEdge(null, ptoF, v1, "own");

											Edge s = graph
													.addEdge(null, person3,
															ptoF, "relational");
											s.setProperty("relationtype",
													relationtype);
											if (nickname != null
													&& !nickname.equals("")) {
												s.setProperty("nickname",
														nickname);
											}

										}

									} else if (v1.getProperty("type").equals(
											"IM")
											&& !v1.getProperty("numid").equals(
													friendNum)) {
										Vertex ptos = null;
										try {
											ptos = (Vertex) tmpto
													.query()
													.labels(new String[] { "own" })
													.vertices().iterator()
													.next();
										} catch (java.util.NoSuchElementException e) {
											e.printStackTrace();
										}
										if (ptos != null) {
											qqEveve = graph.addVertex(null);
											qqEveve.setProperty("type", "IM");
											qqEveve.setProperty("domain",
													domain);
											qqEveve.setProperty("numid",
													friendNum);
											graph.addEdge(null, ptos, qqEveve,
													"own");

											Edge s = graph
													.addEdge(null, person3,
															ptos, "relational");
											s.setProperty("relationtype",
													relationtype);
											if (nickname != null
													&& !nickname.equals("")) {
												s.setProperty("nickname",
														nickname);
											}

										}
									}
								}

							}
						} else {

							Vertex p = graph.addVertex(null);
							p.setProperty("type", "Person");

							if (friendNum != null && !friendNum.equals("")) {
								qqEveve = graph.addVertex(null);
								qqEveve.setProperty("type", "IM");
								qqEveve.setProperty("domain", domain);
								qqEveve.setProperty("numid", friendNum);
								graph.addEdge(null, p, qqEveve, "own");

								Edge s = graph.addEdge(null, person3, p,
										"relational");
								s.setProperty("relationtype", relationtype);
								if (nickname != null && !nickname.equals("")) {
									s.setProperty("nickname", nickname);
								}

							}
						}
					}
				}

			}

		} else {
			Vertex qqEveve = null;
			Vertex person2 = graph.addVertex(null);// 人物对象
			person2.setProperty("type", "Person");
			if (name != null && !name.equals("")) {
				person2.setProperty("name", name);
			}
			if (idcard != null && !idcard.equals("")) {
				person2.setProperty("idcard", idcard);
			}
			if (country != null && !country.equals("")) {
				person2.setProperty("country", country);
			}

			if (numid != null && !"".equals(numid)) {
				Vertex Qq3 = graph.addVertex(null);// QQ事件
				Qq3.setProperty("type", "IM");
				if (domain != null && !"".equals(domain)) {
					Qq3.setProperty("domain", domain);
				}

				if (numid != null && !"".equals(numid)) {
					Qq3.setProperty("numid", numid);
				}
				graph.addEdge(null, person2, Qq3, "own");
			}

			if (friendNum != null && !friendNum.equals("")) {

				Iterable personto = graph.query().has("numid", friendNum)
						.vertices();
				Iterator gf = personto.iterator();
				if (gf.hasNext()) {
					Vertex tmpto = (Vertex) gf.next();
					for (Iterator iterator1 = personto.iterator(); iterator1
							.hasNext();) {
						Vertex v1 = (Vertex) iterator1.next();
						if (friendNum != null && !"".equals(friendNum)) {
							if (v1.getProperty("type").equals("IM")
									&& v1.getProperty("numid")
											.equals(friendNum)) {

								Vertex pto = null;
								try {
									pto = (Vertex) tmpto.query()
											.labels(new String[] { "own" })
											.vertices().iterator().next();
								} catch (java.util.NoSuchElementException e) {
									e.printStackTrace();
								}
								if (pto != null) {
									Edge s = graph.addEdge(null, person2, pto,
											"relational");
									s.setProperty("relationtype", relationtype);
									if (nickname != null
											&& !nickname.equals("")) {
										s.setProperty("nickname", nickname);
									}
								} else {
									Vertex ptoF = graph.addVertex(null);
									ptoF.setProperty("type", "Person");
									graph.addEdge(null, ptoF, v1, "own");
									Edge s = graph.addEdge(null, person2, ptoF,
											"relational");
									s.setProperty("relationtype", relationtype);
									if (nickname != null
											&& !nickname.equals("")) {
										s.setProperty("nickname", nickname);
									}

								}
							} else if (v1.getProperty("type").equals("IM")
									&& !v1.getProperty("numid").equals(
											friendNum)) {
								Vertex pto = null;
								try {
									pto = (Vertex) tmpto.query()
											.labels(new String[] { "own" })
											.vertices().iterator().next();
								} catch (java.util.NoSuchElementException e) {
									e.printStackTrace();
								}
								if (pto != null) {
									qqEveve = graph.addVertex(null);
									qqEveve.setProperty("type", "IM");
									qqEveve.setProperty("domain", domain);
									qqEveve.setProperty("numid", friendNum);
									graph.addEdge(null, pto, qqEveve, "own");

									Edge s = graph.addEdge(null, person2, pto,
											"relational");
									s.setProperty("relationtype", relationtype);
									if (nickname != null
											&& !nickname.equals("")) {
										s.setProperty("nickname", nickname);
									}
								}

							}
						}

					}
				} else {

					Vertex p = graph.addVertex(null);
					p.setProperty("type", "Person");

					if (friendNum != null && !friendNum.equals("")) {
						qqEveve = graph.addVertex(null);
						qqEveve.setProperty("type", "IM");
						qqEveve.setProperty("domain", domain);
						qqEveve.setProperty("numid", friendNum);
						graph.addEdge(null, p, qqEveve, "own");

						Edge s = graph.addEdge(null, person2, p, "relational");
						s.setProperty("relationtype", relationtype);
						if (nickname != null && !nickname.equals("")) {
							s.setProperty("nickname", nickname);
						}

					}
				}
			}

		}
		graph.commit();

		graph.shutdown();

	}

}
