package org.egov.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TenantTest {

    @Test
    public void test_equality_should_return_true_when_both_instances_have_same_tenant_id() {
        final Tenant tenant1 = new Tenant("tenant1");
        final Tenant tenant2 = new Tenant("tenant1");

        Assertions.assertTrue(tenant1.equals(tenant2));
    }

    @Test
    public void test_hashcode_should_be_same_when_both_instances_have_same_tenant_id() {
        final Tenant tenant1 = new Tenant("tenant1");
        final Tenant tenant2 = new Tenant("tenant1");

        Assertions.assertEquals(tenant1.hashCode(), tenant2.hashCode());
    }

    @Test
    public void test_equality_should_return_false_when_both_instances_have_different_tenant_id() {
        final Tenant tenant1 = new Tenant("tenant1");
        final Tenant tenant2 = new Tenant("tenant2");

        Assertions.assertFalse(tenant1.equals(tenant2));
    }

    @Test
    public void test_hashcode_should_be_different_when_both_instances_have_different_tenant_id() {
        final Tenant tenant1 = new Tenant("tenant1");
        final Tenant tenant2 = new Tenant("tenant2");

        Assertions.assertNotEquals(tenant1.hashCode(), tenant2.hashCode());
    }

    @Test
    public void test_should_split_name_spaced_tenant_code_to_hierarchy_list() {
        final Tenant tenant = new Tenant("a.b.c.d");

        final List<Tenant> tenantHierarchyList = tenant.getTenantHierarchy();

        Assertions.assertEquals(5, tenantHierarchyList.size());
        Assertions.assertEquals(new Tenant("a.b.c.d"), tenantHierarchyList.get(0));
        Assertions.assertEquals(new Tenant("a.b.c"), tenantHierarchyList.get(1));
        Assertions.assertEquals(new Tenant("a.b"), tenantHierarchyList.get(2));
        Assertions.assertEquals(new Tenant("a"), tenantHierarchyList.get(3));
        Assertions.assertEquals(new Tenant("default"), tenantHierarchyList.get(4));
    }

    @Test
    public void test_should_return_default_tenant_along_with_tenant_id_when_namespace_not_present() {
        final Tenant tenant = new Tenant("a");

        final List<Tenant> tenantHierarchyList = tenant.getTenantHierarchy();

        Assertions.assertEquals(2, tenantHierarchyList.size());
        Assertions.assertEquals(new Tenant("a"), tenantHierarchyList.get(0));
        Assertions.assertEquals(new Tenant("default"), tenantHierarchyList.get(1));
    }

    @Test
    public void test_is_default_tenant_should_return_true_when_tenant_is_default() {
        final Tenant tenant = new Tenant("default");

        Assertions.assertTrue(tenant.isDefaultTenant());
    }

    @Test
    public void test_is_default_tenant_should_return_false_when_tenant_is_not_default() {
        final Tenant tenant = new Tenant("a.b.c");

        Assertions.assertFalse(tenant.isDefaultTenant());
    }

    @Test
    public void test_is_more_specific_compared_to_should_return_false_when_other_tenant_has_same_id() {
        final Tenant tenant = new Tenant("a.b.c");

        Assertions.assertFalse(tenant.isMoreSpecificComparedTo(new Tenant("a.b.c")));
    }

    @Test
    public void test_is_more_specific_compared_to_should_return_false_when_other_tenant_is_more_specific_in_hierarchy() {
        final Tenant tenant = new Tenant("a.b.c");

        Assertions.assertFalse(tenant.isMoreSpecificComparedTo(new Tenant("a.b.c.d")));
    }

    @Test
    public void test_is_more_specific_compared_to_should_return_false_when_other_tenant_is_of_different_hierarchy() {
        final Tenant tenant = new Tenant("a.b.c");

        Assertions.assertFalse(tenant.isMoreSpecificComparedTo(new Tenant("d.e")));
    }

    @Test
    public void test_is_more_specific_compared_to_should_return_true_when_other_tenant_is_less_specific_in_hierarchy() {
        final Tenant tenant = new Tenant("a.b.c.d");

        Assertions.assertTrue(tenant.isMoreSpecificComparedTo(new Tenant("a.b")));
    }

}